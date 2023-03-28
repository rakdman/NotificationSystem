package com.debitnotification.springserver.process;


import com.debitnotification.springserver.workflowprocessinstance.WorkflowProcessInstance;
import com.debitnotification.springserver.workflowprocessinstance.WorkflowProcessInstanceRepo;
import com.debitnotification.springserver.workflowprocessinstance.WorkflowProcessInstanceStep;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@Configuration
@Slf4j
public class WorkflowProcessingBatchJob {

    private final WorkflowProcessInstanceRepo workflowProcessInstanceRepo;
    private final SendEmailAction sendEmailAction;

    @Scheduled(fixedRate = 3600000)
    public void process() {
        log.info("Starting the instance processing");

        List<WorkflowProcessInstance> inProgressWorkflowProcessInstances = workflowProcessInstanceRepo.findAll().stream()
                .filter(instance -> !instance.getInstanceStatus().equals("ended"))
                .filter(instance -> instance.getOpenAmount() > 0).collect(Collectors.toList());

        for (WorkflowProcessInstance workflowProcessInstance : inProgressWorkflowProcessInstances) {
            WorkflowProcessInstanceStep pendingStep = workflowProcessInstance.getWorkflowProcessInstanceStep()
                    .stream()
                    .filter(s -> s.getStepStatus().equals("pending")).findFirst().orElse(null);

            if (workflowProcessInstance != null && pendingStep != null) {
                boolean status = Boolean.FALSE;
                status = sendEmailAction.sendNotificationEmail(workflowProcessInstance, pendingStep);
                if (status) {
                    pendingStep.setStepStatus("completed");
                }

                workflowProcessInstance.getWorkflowProcessInstanceStep().add(pendingStep);

                boolean allCompleted = Boolean.FALSE;

                allCompleted = !workflowProcessInstance.getWorkflowProcessInstanceStep().stream()
                        .filter(a -> a.getStepStatus().equals("pending"))
                        .findAny().isPresent();

                if (allCompleted) workflowProcessInstance.setInstanceStatus("finished");

                workflowProcessInstanceRepo.save(workflowProcessInstance);

            }


        }
    }


}
