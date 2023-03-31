package com.debitnotification.springserver.workflowprocessinstance;

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
public class WorkflowProcessInstanceService {

    @Value("${data.customer.filename}")
    String fileName;

    @Value("${data.customer.paymentfilename}")
    String paymentFileName;
    WorkflowProcessInstanceRepo workflowProcessInstanceRepo;
    WorkflowDefinitionRepo workflowDefinitionRepo;


    public WorkflowProcessInstanceService(WorkflowProcessInstanceRepo workflowProcessInstanceRepo, WorkflowDefinitionRepo workflowDefinitionRepo) {
        this.workflowProcessInstanceRepo = workflowProcessInstanceRepo;
        this.workflowDefinitionRepo = workflowDefinitionRepo;
    }


    public void loadDataFile() throws IOException {
        ClassPathResource classPathResource = new ClassPathResource(fileName);
        File file = classPathResource.getFile();
        ObjectMapper objectMapper = new ObjectMapper();
        TypeReference<List<WorkflowProcessInstance>> typeListOfCustomer = new TypeReference<List<WorkflowProcessInstance>>() {
        };
        List<WorkflowProcessInstance> listOfCustomer = objectMapper.readValue(file, typeListOfCustomer);
        workflowProcessInstanceRepo.saveAll(listOfCustomer);

        for (WorkflowProcessInstance customer : listOfCustomer) {
            processCustomerData(customer);
        }

    }

    private void processCustomerData(WorkflowProcessInstance customer) {
        List<WorkflowProcessInstanceStep> listOfInstanceWorkflowProcessInstanceStep = new ArrayList<>();

        String workflowName = customer.getWorkflowName();
        Optional<WorkflowDefinition> workflowDefinition = workflowDefinitionRepo.findByWorkflowTemplateName(workflowName);

        Optional<List<WorkflowDefinitionStep>> workflowDefinitionSteps = workflowDefinition.map(WorkflowDefinition::getWorkflowDefinitionStep);


        if (workflowDefinitionSteps.isPresent()) {
            scheduleWorkflowInstanceSteps(listOfInstanceWorkflowProcessInstanceStep, workflowDefinitionSteps);
        }

        customer.setWorkflowProcessInstanceStep(listOfInstanceWorkflowProcessInstanceStep);
        customer.setInstanceStatus(InstanceStatusEnum.LOADED);
        workflowProcessInstanceRepo.save(customer);

    }

    private void scheduleWorkflowInstanceSteps(List<WorkflowProcessInstanceStep> listOfInstanceWorkflowProcessInstanceStep, Optional<List<WorkflowDefinitionStep>> workflowDefinitionSteps) {
        WorkflowProcessInstanceStep workflowProcessInstanceStep;
        for (WorkflowDefinitionStep step : workflowDefinitionSteps.get()) {
            workflowProcessInstanceStep = new WorkflowProcessInstanceStep();
            workflowProcessInstanceStep.setStepName(step.getWorkflowTemplateStepName());
            workflowProcessInstanceStep.setStepStatus(InstanceStepStatusEnum.PENDING);
            long time = 24 * 60 * 60 * 1000 * step.getWorkflowTemplateStepWait();
            workflowProcessInstanceStep.setStepScheduleDate(new Date(new Date().getTime() + time));
            listOfInstanceWorkflowProcessInstanceStep.add(workflowProcessInstanceStep);
        }
    }


    public void loadPaymentFile() throws IOException {
        ClassPathResource classPathResource = new ClassPathResource(paymentFileName);
        File file = classPathResource.getFile();
        ObjectMapper objectMapper = new ObjectMapper();
        TypeReference<List<BillPayment>> listOfPayment = new TypeReference<List<BillPayment>>() {
        };
        List<BillPayment> customerBillPayments = objectMapper.readValue(file, listOfPayment);

        for (BillPayment billPayment : customerBillPayments) {
            processCustomerPayment(billPayment);
        }
    }

    private void processCustomerPayment(BillPayment billPayment) {
        WorkflowProcessInstance customerWorkflowProcessInstance = workflowProcessInstanceRepo.findByBillId(billPayment.getBillId());
        if (billPayment != null) {
            double updatedOpenAmount = customerWorkflowProcessInstance.getOpenAmount() - Double.parseDouble(billPayment.getPaymentAmount());
            customerWorkflowProcessInstance.setOpenAmount(updatedOpenAmount);
        }

        if (customerWorkflowProcessInstance.getOpenAmount() <= 0
                && customerWorkflowProcessInstance.getInstanceStatus().equals(InstanceStatusEnum.INPROGRESS)) {
            customerWorkflowProcessInstance.setInstanceStatus(InstanceStatusEnum.FINISHED);
            customerWorkflowProcessInstance.setOpenAmount(0);

            for (WorkflowProcessInstanceStep step : customerWorkflowProcessInstance.getWorkflowProcessInstanceStep()) {
                step.setStepStatus(InstanceStepStatusEnum.COMPLETED);
            }
        }
        workflowProcessInstanceRepo.save(customerWorkflowProcessInstance);
    }

    public List<WorkflowProcessInstance> getAllInstances() {
        List<WorkflowProcessInstance> intanceList = workflowProcessInstanceRepo.findAll();
        intanceList.sort((instance1, instance2) -> instance2.getEntryDate().compareTo(instance1.getEntryDate()));
        return intanceList;
    }

    public List<WorkflowProcessInstance> getInstancesByParameters(String firstName, String lastName, String contactNo, String emailId) {
        List<WorkflowProcessInstance> workflowProcessInstanceList = workflowProcessInstanceRepo.findAll();

        if (firstName != null && !firstName.isEmpty())
            workflowProcessInstanceList = workflowProcessInstanceList.parallelStream().filter(a -> a.getFirstName().equalsIgnoreCase(firstName))
                    .collect(Collectors.toList());
        if (lastName != null && !lastName.isEmpty())
            workflowProcessInstanceList = workflowProcessInstanceList.parallelStream().filter(a -> a.getLastName().equalsIgnoreCase(lastName))
                    .collect(Collectors.toList());

        if (contactNo != null && !contactNo.isEmpty())
            workflowProcessInstanceList = workflowProcessInstanceList.parallelStream().filter(a -> a.getContactNo().equalsIgnoreCase(contactNo))
                    .collect(Collectors.toList());

        if (emailId != null && !emailId.isEmpty())
            workflowProcessInstanceList = workflowProcessInstanceList.parallelStream().filter(a -> a.getEmailId().equalsIgnoreCase(emailId))
                    .collect(Collectors.toList());

        workflowProcessInstanceList.sort((instance1, instance2) -> instance2.getEntryDate().compareTo(instance1.getEntryDate()));

        return workflowProcessInstanceList;
    }

    public WorkflowProcessInstance getInstancesById(Integer instanceId) {
        return workflowProcessInstanceRepo.getById(instanceId.longValue());
    }
}

