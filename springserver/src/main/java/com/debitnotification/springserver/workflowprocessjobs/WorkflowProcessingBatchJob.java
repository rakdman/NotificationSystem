package com.debitnotification.springserver.workflowprocessjobs;


import com.debitnotification.springserver.workflowprocessinstance.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class WorkflowProcessingBatchJob {


    private final WorkflowProcessInstanceRepo workflowProcessInstanceRepo;


    private final SendEmailAction sendEmailAction;

    public WorkflowProcessingBatchJob(WorkflowProcessInstanceRepo workflowProcessInstanceRepo, SendEmailAction sendEmailAction) {
        this.workflowProcessInstanceRepo = workflowProcessInstanceRepo;
        this.sendEmailAction = sendEmailAction;
    }

    @Value("${spring.mail.send.email.enable}")
    private boolean enableSendEmail;

    @Scheduled(fixedRate = 360000000)
    public void processWorkflow() {
        List<WorkflowProcessInstance> inProgressWorkflowProcessInstances = extractPendingProcessInstances();
        for (WorkflowProcessInstance workflowProcessInstance : inProgressWorkflowProcessInstances) {
            WorkflowProcessInstanceStep pendingStep = extractProcessInstanceStep(workflowProcessInstance);
            processStep(workflowProcessInstance, pendingStep);
        }
    }

    private WorkflowProcessInstanceStep extractProcessInstanceStep(WorkflowProcessInstance workflowProcessInstance) {
        return workflowProcessInstance.getWorkflowProcessInstanceStep()
                .stream()
                .filter(s -> s.getStepStatus().equals(InstanceStepStatusEnum.PENDING)).findFirst().orElse(null);
    }

    private List<WorkflowProcessInstance> extractPendingProcessInstances() {
        return workflowProcessInstanceRepo.findAll().stream()
                .filter(instance -> !(InstanceStatusEnum.FINISHED).equals(instance.getInstanceStatus())  )
                .filter(instance -> instance.getOpenAmount() > 0).toList();
    }

    private void processStep(WorkflowProcessInstance workflowProcessInstance, WorkflowProcessInstanceStep pendingStep) {
        boolean emailStatus;
        boolean areAllStepCompleted;

        if (pendingStep != null) {

      emailStatus =
          (enableSendEmail
              && sendEmailAction.sendNotificationEmail(workflowProcessInstance, pendingStep));
            if (emailStatus) {
                pendingStep.setStepStatus(InstanceStepStatusEnum.COMPLETED);
            }
            workflowProcessInstance.getWorkflowProcessInstanceStep().add(pendingStep);
            areAllStepCompleted = workflowProcessInstance.getWorkflowProcessInstanceStep().stream()
                    .noneMatch(a -> a.getStepStatus().equals(InstanceStepStatusEnum.PENDING));

            if (areAllStepCompleted) workflowProcessInstance.setInstanceStatus(InstanceStatusEnum.FINISHED);
            workflowProcessInstanceRepo.save(workflowProcessInstance);
        }
    }
}
