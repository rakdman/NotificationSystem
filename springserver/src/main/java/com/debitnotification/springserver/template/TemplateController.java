package com.debitnotification.springserver.template;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin
@RequestMapping("/api/template")
public class TemplateController {
    private final TemplateService templateService;

    @PostMapping("/createtemplate")
    public ResponseEntity<Template> createTemplate(@RequestBody Template template) {
        return ResponseEntity.ok(templateService.createTemplate(template));
    }

    @GetMapping("/emailtemplates")
    public ResponseEntity<List<Template>> findAllTemplates(@RequestParam(required = false) TemplateType templateType) {
        return ResponseEntity.ok(templateService.getAllTemplates(templateType));
    }
}
