package com.debitnotification.springserver.workflowdefinition;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WorkflowDefinitionService {
    private final WorkflowDefinitionRepo workflowDefinitionRepo;

    public WorkflowDefinitionService(WorkflowDefinitionRepo workflowDefinitionRepo) {
        this.workflowDefinitionRepo = workflowDefinitionRepo;
    }

    public WorkflowDefinition createTemplate(WorkflowDefinition templateRequest) {
        WorkflowDefinition workflowDefinition = new WorkflowDefinition();
        workflowDefinition.setWorkflowTemplateName(templateRequest.getWorkflowTemplateName());
        workflowDefinitionRepo.save(templateRequest);

        WorkflowDefinition workflowDefinitionUpdated = workflowDefinitionRepo.findById(templateRequest.getWorkflowTemplateId()).get();

        List<WorkflowDefinitionStep> workflowDefinitionStep = new ArrayList<>();
        for (WorkflowDefinitionStep step : templateRequest.getWorkflowDefinitionStep()) {
            step.setWorkflowDefinition(workflowDefinitionUpdated);
        }

        workflowDefinitionUpdated.setWorkflowDefinitionStep(workflowDefinitionStep);
        workflowDefinitionRepo.save(workflowDefinitionUpdated);
        return workflowDefinitionUpdated;
    }


    public List<WorkflowDefinition> getAllTemplates() {
        return workflowDefinitionRepo.findAll();
    }

    public WorkflowDefinition getOneTemplate(long workflowTemplateId) {
        return workflowDefinitionRepo.findByWorkflowTemplateId(workflowTemplateId);
    }
}
