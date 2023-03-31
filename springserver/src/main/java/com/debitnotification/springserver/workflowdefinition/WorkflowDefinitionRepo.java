package com.debitnotification.springserver.workflowdefinition;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WorkflowDefinitionRepo extends JpaRepository<WorkflowDefinition, Long> {

    public WorkflowDefinition findByWorkflowTemplateId(long workflowTemplateId);

    public Optional<WorkflowDefinition> findByWorkflowTemplateName(String workflowTemplateName);
}
