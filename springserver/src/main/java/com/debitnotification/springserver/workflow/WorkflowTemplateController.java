package com.debitnotification.springserver.workflow;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin("http://localhost:3000/")
@RequestMapping("/api/workflow")
public class WorkflowTemplateController {
    private final WorkflowTemplateService workflowTemplateService;

    @PostMapping("/create")
    public ResponseEntity<WorkflowTemplate> createTemplate(@RequestBody WorkflowTemplate workflowTemplate) {
        return ResponseEntity.ok(workflowTemplateService.createTemplate(workflowTemplate));
    }

    @GetMapping(value = "/readallwftemplate", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<WorkflowTemplate>> findAllTemplates() {
        return ResponseEntity.ok(workflowTemplateService.getAllTemplates());
    }


    @GetMapping(value = "/readoneWFtemplate", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WorkflowTemplate> findOneWorkflowTemplate(@RequestParam long workflowTemplateId) {
        return ResponseEntity.ok(workflowTemplateService.getOneTemplate(workflowTemplateId));
    }
}
