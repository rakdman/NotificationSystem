package com.debitnotification.springserver.notificationtemplate;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

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

    public NotificationTemplate getOneTemplate(long templateId) {
        return notificationTemplateRepo.findById(templateId).get();
    }

    public NotificationTemplate updateTemplate(NotificationTemplate notificationTemplate) {
        notificationTemplateRepo.save(notificationTemplate);
        return notificationTemplate;
    }

    public void deleteTemplate(Long templateId) {
        notificationTemplateRepo.deleteById(templateId);
    }
}
