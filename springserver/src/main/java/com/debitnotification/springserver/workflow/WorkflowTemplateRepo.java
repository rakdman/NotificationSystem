package com.debitnotification.springserver.workflow;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkflowTemplateRepo extends JpaRepository<WorkflowTemplate, Long> {

    public WorkflowTemplate findByWorkflowTemplateId(long workflowTemplateId);

    public WorkflowTemplate findByWorkflowTemplateName(String workflowTemplateName);
}
