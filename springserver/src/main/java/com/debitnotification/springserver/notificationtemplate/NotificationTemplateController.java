package com.debitnotification.springserver.notificationtemplate;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin("http://localhost:3000")
@RequestMapping("/api/template")
public class NotificationTemplateController {
    private final NotificationTemplateService notificationTemplateService;

    @PostMapping("/createtemplate")
    public ResponseEntity<NotificationTemplate> createTemplate(@RequestBody NotificationTemplate notificationTemplate) {
        return ResponseEntity.ok(notificationTemplateService.createTemplate(notificationTemplate));
    }

    @GetMapping("/emailtemplates")
    public ResponseEntity<List<NotificationTemplate>> findAllTemplates(@RequestParam(required = false) NotificationTemplateType notificationTemplateType) {
        return ResponseEntity.ok(notificationTemplateService.getAllTemplates(notificationTemplateType));
    }


    @GetMapping("/readoneemailtemplate")
    public ResponseEntity<NotificationTemplate> findOneTemplate(@RequestParam Long templateId) {
        return ResponseEntity.ok(notificationTemplateService.getOneTemplate(templateId));
    }

    @PutMapping("/updateemailtemplate")
    public ResponseEntity<NotificationTemplate> updateTemplate(@RequestBody NotificationTemplate notificationTemplate) {
        return ResponseEntity.ok(notificationTemplateService.updateTemplate(notificationTemplate));
    }

    @DeleteMapping("/deleteemailtemplate")
    public ResponseEntity deleteTemplate(@RequestParam Long templateId) {
        notificationTemplateService.deleteTemplate(templateId);
        return ResponseEntity.ok(null);
    }


}
