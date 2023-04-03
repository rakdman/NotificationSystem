package com.debitnotification.springserver.integrationtests.notificationtemplate;

import com.debitnotification.springserver.notificationtemplate.NotificationTemplate;
import com.debitnotification.springserver.notificationtemplate.NotificationTemplateType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Rollback
public class TestNotificationTemplate {

    private static NotificationTemplate notificationTemplate;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();


    @BeforeAll
    public static void getTestNotificationTemplate() {
        notificationTemplate = new NotificationTemplate(2000, "EmailTest",
                "Email Test Description", NotificationTemplateType.EMAIL);
    }

    @Test
    public void testCreateTemplate() throws Exception {
        String reqeustAsString = objectMapper.writeValueAsString(notificationTemplate);

        MvcResult mvcResult = mockMvc.perform(post("/api/template/createtemplate")
                        .content(reqeustAsString)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        String responseAsString = mvcResult.getResponse().getContentAsString();
        NotificationTemplate responseTemplate = objectMapper.readValue(responseAsString, NotificationTemplate.class);
        Assertions.assertEquals(responseTemplate.getNotificationTemplateName(), notificationTemplate.getNotificationTemplateName());
        Assertions.assertEquals(responseTemplate.getNotificationTemplateText(), notificationTemplate.getNotificationTemplateText());

    }

    @Test
    public void testUpdateTemplate() throws Exception {

        String reqeustAsString = objectMapper.writeValueAsString(notificationTemplate);

        MvcResult mvcResult = mockMvc.perform(post("/api/template/createtemplate")
                        .content(reqeustAsString)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();


        notificationTemplate.setNotificationTemplateName("UpdateTemplateName");
        notificationTemplate.setNotificationTemplateText("Update Template Text");

        mvcResult = mockMvc.perform(put("/api/template/updateemailtemplate")
                        .content(reqeustAsString)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        String responseAsString = mvcResult.getResponse().getContentAsString();
        NotificationTemplate responseObject = objectMapper
                .readValue(responseAsString, NotificationTemplate.class);

        Assertions.assertEquals(notificationTemplate.getNotificationTemplateName(), "UpdateTemplateName");
        Assertions.assertEquals(notificationTemplate.getNotificationTemplateText(), "Update Template Text");
    }

    @Test
    public void testReadTemplate() throws Exception {

        String reqeustAsString = objectMapper.writeValueAsString(notificationTemplate);

        MvcResult mvcResult = mockMvc.perform(post("/api/template/createtemplate")
                        .content(reqeustAsString)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        String responseAsString = mvcResult.getResponse().getContentAsString();
        NotificationTemplate responseObject = objectMapper
                .readValue(responseAsString, NotificationTemplate.class);


        Long templateId = responseObject.getNotificationTemplateId();

        MvcResult mvcReadResult = mockMvc.perform(get("/api/template/readoneemailtemplate")
                        .param("notificationTemplateId", templateId.toString()))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        responseAsString = mvcReadResult.getResponse().getContentAsString();
        responseObject = objectMapper
                .readValue(responseAsString, NotificationTemplate.class);

        Assertions.assertEquals(notificationTemplate.getNotificationTemplateName(), "UpdateTemplateName");
        Assertions.assertEquals(notificationTemplate.getNotificationTemplateText(), "Update Template Text");

//        String readResponseAsString = mvcReadResult.getResponse().getContentAsString();
//
//        NotificationTemplate notificationTemplateRead = objectMapper
//                .readValue(readResponseAsString, NotificationTemplate.class);
//        Assertions.assertEquals(notificationTemplateRead.getNotificationTemplateName(), notificationTemplate.getNotificationTemplateName());
//        Assertions.assertEquals(notificationTemplateRead.getNotificationTemplateText(), notificationTemplate.getNotificationTemplateText());

    }


    @Test
    public void testDeleteTemplate() throws Exception {

        String reqeustAsString = objectMapper.writeValueAsString(notificationTemplate);

        MvcResult mvcResult = mockMvc.perform(post("/api/template/createtemplate")
                        .content(reqeustAsString)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();


        String responseAsString = mvcResult.getResponse().getContentAsString();
        NotificationTemplate responseObject = objectMapper
                .readValue(responseAsString, NotificationTemplate.class);

        Long templateId = responseObject.getNotificationTemplateId();

        MvcResult mvcDeleteResult = mockMvc.perform(delete("/api/template/deleteemailtemplate")
                        .param("templateId", templateId.toString()))
                .andExpect(status().isOk())
                .andDo(print()).andReturn();

        int status = mvcDeleteResult.getResponse().getStatus();
        Assertions.assertEquals(HttpStatus.OK.value(), status);


    }

}
