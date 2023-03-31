package com.debitnotification.springserver.workflowprocessinstance;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class WorkflowProcessInstance {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long instanceId;
    String firstName;
    String lastName;
    String contactNo;
    String emailId;
    String workflowId;
    String workflowName;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn
    List<WorkflowProcessInstanceStep> workflowProcessInstanceStep;
    InstanceStatusEnum instanceStatus;
    String allowedRoles;
    Date entryDate = new Date();

    String billId;
    double openAmount;

}
