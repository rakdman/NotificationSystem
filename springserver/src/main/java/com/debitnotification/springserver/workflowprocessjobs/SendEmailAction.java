package com.debitnotification.springserver.process;

import com.debitnotification.springserver.workflowprocessinstance.WorkflowProcessInstance;
import com.debitnotification.springserver.workflowprocessinstance.WorkflowProcessInstanceStep;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class SendEmailAction {
    private final JavaMailSender mailSender;

    public SendEmailAction(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }


    public boolean sendNotificationEmail(WorkflowProcessInstance workflowProcessInstance, WorkflowProcessInstanceStep step) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo("dhim.rakesh@gmail.com");
        simpleMailMessage.setSubject(step.getStepName());
        simpleMailMessage.setText("Dear "
                + workflowProcessInstance.getLastName()
                + " \nThe open bill amount "
                + workflowProcessInstance.getOpenAmount()
                + " is pending for payment. Please pay."
                + "\nRegards "
                + "\nNotificationSystem");
        mailSender.send(simpleMailMessage);
        return true;
    }
}
