package com.debitnotification.springserver.template;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin("http://localhost:3000")
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


    @GetMapping("/readoneemailtemplate")
    public ResponseEntity<Template> findOneTemplate(@RequestParam Long templateId) {
        return ResponseEntity.ok(templateService.getOneTemplate(templateId));
    }

    @PutMapping("/updateemailtemplate")
    public ResponseEntity<Template> updateTemplate(@RequestBody Template template) {
        return ResponseEntity.ok(templateService.updateTemplate(template));
    }

    @DeleteMapping("/deleteemailtemplate")
    public ResponseEntity deleteTemplate(@RequestParam Long templateId) {
        templateService.deleteTemplate(templateId);
        return ResponseEntity.ok(null);
    }


}
