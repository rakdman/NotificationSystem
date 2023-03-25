package com.debitnotification.springserver.template;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Template {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long templateId;
    private String templateName;
    private String templateText;
    private TemplateType templateType;

}
