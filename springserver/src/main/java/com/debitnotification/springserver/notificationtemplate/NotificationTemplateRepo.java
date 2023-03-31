package com.debitnotification.springserver.notificationtemplate;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationTemplateRepo extends JpaRepository<NotificationTemplate, Long> {
    List<NotificationTemplate> findByNotificationTemplateType(NotificationTemplateType notificationTemplateType);
}
