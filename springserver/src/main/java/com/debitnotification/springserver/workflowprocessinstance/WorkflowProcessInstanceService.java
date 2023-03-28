package com.debitnotification.springserver.instance;

import com.debitnotification.springserver.workflowdefinition.WorkflowDefinition;
import com.debitnotification.springserver.workflowdefinition.WorkflowDefinitionRepo;
import com.debitnotification.springserver.workflowdefinition.WorkflowDefinitionStep;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


@Service
@Slf4j
public class InstanceService {

    @Value("${data.customer.filename}")
    String fileName;

    @Value("${data.customer.paymentfilename}")
    String paymentFileName;
    InstanceRepo instanceRepo;
    WorkflowDefinitionRepo workflowDefinitionRepo;


    public InstanceService(InstanceRepo instanceRepo, WorkflowDefinitionRepo workflowDefinitionRepo) {
        this.instanceRepo = instanceRepo;
        this.workflowDefinitionRepo = workflowDefinitionRepo;
    }


    public void loadDataFile() throws IOException {
        ClassPathResource classPathResource = new ClassPathResource(fileName);
        File file = classPathResource.getFile();
        ObjectMapper objectMapper = new ObjectMapper();
        TypeReference<List<Instance>> typeListOfCustomer = new TypeReference<List<Instance>>() {
        };
        List<Instance> listOfCustomer = objectMapper.readValue(file, typeListOfCustomer);
        instanceRepo.saveAll(listOfCustomer);

        for (Instance customer : listOfCustomer) {
            processCustomerData(customer);
        }

    }

    private void processCustomerData(Instance customer) {
        String workflowName = customer.getWorkflowName();
        WorkflowDefinition workflowDefinition = workflowDefinitionRepo.findByWorkflowTemplateName(workflowName);

        List<WorkflowStep> listOfInstanceWorkflowStep = new ArrayList<>();
        if (workflowName.equals(workflowDefinition.getWorkflowTemplateName())) {
            List<WorkflowDefinitionStep> workflowDefinitionStep = workflowDefinition.getWorkflowDefinitionStep();

            WorkflowStep workflowStep;
            for (WorkflowDefinitionStep step : workflowDefinitionStep) {
                workflowStep = new WorkflowStep();
                workflowStep.setStepName(step.getWorkflowTemplateStepName());
                workflowStep.setStepStatus("pending");
                long time = 24 * 60 * 60 * 1000 * step.getWorkflowTemplateStepWait();
                workflowStep.setStepScheduleDate(new Date(new Date().getTime() + time));
//                workflowStep.setInstance(customer);
                listOfInstanceWorkflowStep.add(workflowStep);
            }
            customer.setWorkflowStep(listOfInstanceWorkflowStep);
            customer.setInstanceStatus("inprogress");
            instanceRepo.save(customer);
        }

    }

    public void loadPaymentFile() throws IOException {
        ClassPathResource classPathResource = new ClassPathResource(paymentFileName);
        File file = classPathResource.getFile();
        ObjectMapper objectMapper = new ObjectMapper();
        TypeReference<List<Payment>> listOfPayment = new TypeReference<List<Payment>>() {
        };
        List<Payment> customerPayments = objectMapper.readValue(file, listOfPayment);

        for (Payment payment : customerPayments) {
            processCustomerPayment(payment);
        }
    }

    private void processCustomerPayment(Payment payment) {
        Instance customerInstance = instanceRepo.findByBillId(payment.getBillId());
        if (payment != null) {
            double updatedOpenAmount = customerInstance.getOpenAmount() - Double.parseDouble(payment.getPaymentAmount());
            customerInstance.setOpenAmount(updatedOpenAmount);
        }

        if (customerInstance.getOpenAmount() <= 0
                && customerInstance.getInstanceStatus().equalsIgnoreCase("inprogress")) {
            customerInstance.setInstanceStatus("ended");
            customerInstance.setOpenAmount(0);

            for (WorkflowStep step : customerInstance.getWorkflowStep()) {
                step.setStepStatus("ended");
            }
        }
        instanceRepo.save(customerInstance);
    }

    public List<Instance> getAllInstances() {
        List<Instance> intanceList = instanceRepo.findAll();
        intanceList.sort((instance1, instance2) -> instance2.getEntryDate().compareTo(instance1.getEntryDate()));
        return intanceList;
    }

    public List<Instance> getInstancesByParameters(String firstName, String lastName, String contactNo, String emailId) {
        List<Instance> instanceList = instanceRepo.findAll();

        if (firstName != null && !firstName.isEmpty())
            instanceList = instanceList.parallelStream().filter(a -> a.getFirstName().equalsIgnoreCase(firstName))
                    .collect(Collectors.toList());
        if (lastName != null && !lastName.isEmpty())
            instanceList = instanceList.parallelStream().filter(a -> a.getLastName().equalsIgnoreCase(lastName))
                    .collect(Collectors.toList());

        if (contactNo != null && !contactNo.isEmpty())
            instanceList = instanceList.parallelStream().filter(a -> a.getContactNo().equalsIgnoreCase(contactNo))
                    .collect(Collectors.toList());

        if (emailId != null && !emailId.isEmpty())
            instanceList = instanceList.parallelStream().filter(a -> a.getEmailId().equalsIgnoreCase(emailId))
                    .collect(Collectors.toList());

        instanceList.sort((instance1, instance2) -> instance2.getEntryDate().compareTo(instance1.getEntryDate()));

        return instanceList;
    }

    public Instance getInstancesById(Integer instanceId) {
        return instanceRepo.getById(instanceId.longValue());
    }
}

