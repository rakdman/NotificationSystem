package com.debitnotification.springserver.process;

import com.debitnotification.springserver.instance.Instance;
import com.debitnotification.springserver.instance.WorkflowStep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class SendEmail {
    private final JavaMailSender mailSender;

    public SendEmail(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }


    public boolean sendNotificationEmail(Instance instance, WorkflowStep step) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo("dhim.rakesh@gmail.com");
        simpleMailMessage.setSubject(step.getStepName());
        simpleMailMessage.setText("Dear " + instance.getLastName() + " This is test email from notification ");
        mailSender.send(simpleMailMessage);
        return true;
    }
}
