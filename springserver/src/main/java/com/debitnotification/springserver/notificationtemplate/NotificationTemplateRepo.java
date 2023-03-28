package com.debitnotification.springserver.template;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationTemplateRepo extends JpaRepository<NotificationTemplate, Long> {
    List<NotificationTemplate> findByTemplateType(NotificationTemplateTypeEnum notificationTemplateTypeEnum);
}
