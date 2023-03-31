package com.debitnotification.springserver.workflowprocessjobs;


import com.debitnotification.springserver.workflowprocessinstance.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

@Data
@AllArgsConstructor
@Configuration
@Slf4j
public class WorkflowProcessingBatchJob {
    private final WorkflowProcessInstanceRepo workflowProcessInstanceRepo;
    private final SendEmailAction sendEmailAction;

    @Scheduled(fixedRate = 3600000)
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
                .filter(s -> s.getStepStatus().equals("pending")).findFirst().orElse(null);
    }

    private List<WorkflowProcessInstance> extractPendingProcessInstances() {
        return workflowProcessInstanceRepo.findAll().stream()
                .filter(instance -> !instance.getInstanceStatus().equals("ended"))
                .filter(instance -> instance.getOpenAmount() > 0).toList();
    }

    private void processStep(WorkflowProcessInstance workflowProcessInstance, WorkflowProcessInstanceStep pendingStep) {
        boolean emailStatus;
        boolean areAllStepCompleted;

        if (pendingStep != null) {
            emailStatus = sendEmailAction.sendNotificationEmail(workflowProcessInstance, pendingStep);
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
