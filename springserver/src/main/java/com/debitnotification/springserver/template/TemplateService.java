package com.debitnotification.springserver.template;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TemplateService {
    private final TemplateRepo templateRepo;

    public TemplateService(TemplateRepo templateRepo) {
        this.templateRepo = templateRepo;
    }

    public Template createTemplate(Template template) {
        templateRepo.save(template);
        return template;
    }

    public List<Template> getAllTemplates(TemplateType templateType) {
        if (templateType != null)
            return templateRepo.findByTemplateType(templateType);

        return templateRepo.findAll();
    }

}
