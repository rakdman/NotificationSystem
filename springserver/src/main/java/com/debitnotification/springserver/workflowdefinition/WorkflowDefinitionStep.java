package com.debitnotification.springserver.workflowdefinition;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class WorkflowDefinitionStep {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long workflowTemplateStepId;
    private String workflowTemplateStepName;
    private int workflowTemplateStepWait;
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "workflowTemplateId", referencedColumnName = "workflowTemplateId")
    private WorkflowDefinition workflowDefinition;

}
