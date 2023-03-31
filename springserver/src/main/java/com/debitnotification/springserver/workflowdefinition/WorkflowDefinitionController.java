package com.debitnotification.springserver.workflowdefinition;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin("http://localhost:3000/")
@RequestMapping("/api/workflow")
public class WorkflowDefinitionController {
    private final WorkflowDefinitionService workflowDefinitionService;

    @PostMapping("/create")
    public ResponseEntity<WorkflowDefinition> createTemplate(@RequestBody WorkflowDefinition workflowDefinition) {
        return ResponseEntity.ok(workflowDefinitionService.createTemplate(workflowDefinition));
    }

    @GetMapping(value = "/readallwftemplate", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<WorkflowDefinition>> findAllTemplates() {
        return ResponseEntity.ok(workflowDefinitionService.getAllTemplates());
    }


    @GetMapping(value = "/readoneWFtemplate", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WorkflowDefinition> findOneWorkflowTemplate(@RequestParam long workflowTemplateId) {
        return ResponseEntity.ok(workflowDefinitionService.getOneTemplate(workflowTemplateId));
    }
}
