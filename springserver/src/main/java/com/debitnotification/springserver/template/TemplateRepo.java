package com.debitnotification.springserver.template;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TemplateRepo extends JpaRepository<Template, Long> {
    List<Template> findByTemplateType(TemplateType templateType);
}
