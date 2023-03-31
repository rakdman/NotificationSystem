package com.debitnotification.springserver.workflowdefinition;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class WorkflowDefinition {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long workflowTemplateId;
    private String workflowTemplateName;

    @JsonManagedReference
    @OneToMany(mappedBy = "workflowDefinition", cascade = CascadeType.ALL)
    private List<WorkflowDefinitionStep> workflowDefinitionStep;


}
