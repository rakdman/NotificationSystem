package com.debitnotification.springserver.workflow;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WorkflowTemplateService {
    private final WorkflowTemplateRepo workflowTemplateRepo;

    public WorkflowTemplateService(WorkflowTemplateRepo workflowTemplateRepo) {
        this.workflowTemplateRepo = workflowTemplateRepo;
    }

    public WorkflowTemplate createTemplate(WorkflowTemplate templateRequest) {
        WorkflowTemplate workflowTemplate = new WorkflowTemplate();
        workflowTemplate.setWorkflowTemplateName(templateRequest.getWorkflowTemplateName());
        workflowTemplateRepo.save(templateRequest);

        WorkflowTemplate workflowTemplateUpdated = workflowTemplateRepo.findById(templateRequest.getWorkflowTemplateId()).get();

        List<WorkflowTemplateStep> workflowTemplateStep = new ArrayList<>();
        for (WorkflowTemplateStep step : templateRequest.getWorkflowTemplateStep()) {
            step.setWorkflowTemplate(workflowTemplateUpdated);
        }

        workflowTemplateUpdated.setWorkflowTemplateStep(workflowTemplateStep);
        workflowTemplateRepo.save(workflowTemplateUpdated);
        return workflowTemplateUpdated;
    }


    public List<WorkflowTemplate> getAllTemplates() {
        return workflowTemplateRepo.findAll();
    }

    public WorkflowTemplate getOneTemplate(long workflowTemplateId) {
        return workflowTemplateRepo.findByWorkflowTemplateId(workflowTemplateId);
    }
}
