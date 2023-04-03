package com.debitnotification.springserver.notificationtemplate;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;
import java.util.Optional;

@Service
@CrossOrigin
public class NotificationTemplateService {
    private final NotificationTemplateRepo notificationTemplateRepo;

    public NotificationTemplateService(NotificationTemplateRepo notificationTemplateRepo) {
        this.notificationTemplateRepo = notificationTemplateRepo;
    }

    public NotificationTemplate createTemplate(NotificationTemplate notificationTemplate) {
        notificationTemplateRepo.save(notificationTemplate);
        return notificationTemplate;
    }

    public List<NotificationTemplate> getAllTemplates(NotificationTemplateType notificationTemplateType) {
        if (notificationTemplateType != null)
            return notificationTemplateRepo.findByNotificationTemplateType(notificationTemplateType);

        return notificationTemplateRepo.findAll();
    }

    public NotificationTemplate getOneTemplate(long notificationTemplateId) {
        Optional<NotificationTemplate> notificationTemplate = notificationTemplateRepo.findById(notificationTemplateId);
        if (notificationTemplate.isPresent()) {
            return notificationTemplate.get();
        }
        return null;
    }

    public void deleteTemplate(Long templateId) {
        notificationTemplateRepo.deleteById(templateId);
    }
}
