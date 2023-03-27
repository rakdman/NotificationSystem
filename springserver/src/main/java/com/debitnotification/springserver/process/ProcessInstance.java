package com.debitnotification.springserver.process;


import com.debitnotification.springserver.instance.Instance;
import com.debitnotification.springserver.instance.InstanceRepo;
import com.debitnotification.springserver.instance.WorkflowStep;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@Configuration
@Slf4j
public class ProcessInstance {

    private final InstanceRepo instanceRepo;
    private final SendEmail sendEmail;

    @Scheduled(fixedRate = 50000)
    public void process() {
        log.info("Starting the instance processing");

        List<Instance> inProgressInstances = instanceRepo.findAll().stream()
                .filter(instance -> !instance.getInstanceStatus().equals("ended"))
                .filter(instance -> instance.getOpenAmount() > 0).collect(Collectors.toList());

        for (Instance instance : inProgressInstances) {
            WorkflowStep pendingStep = instance.getWorkflowStep()
                    .stream()
                    .filter(s -> s.getStepStatus().equals("pending"))
                    .findFirst().get();

            boolean status = sendEmail.sendNotificationEmail(instance, pendingStep);
            if (status) {
                pendingStep.setStepStatus("completed");
            }

            instance.getWorkflowStep().add(pendingStep);

            boolean allCompleted = !instance.getWorkflowStep().stream().filter(i -> i.getStepStatus().equals("pending")).findAny().isPresent();
            if (allCompleted) instance.setInstanceStatus("finished");

            instanceRepo.save(instance);
        }
    }


}
