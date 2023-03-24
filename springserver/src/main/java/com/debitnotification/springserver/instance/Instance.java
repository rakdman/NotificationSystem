package com.debitnotification.springserver.instance;

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
public class Instance {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long instanceId;
    String firstName;
    String lastName;
    String contactNo;
    String emailId;
    String workflowId;
    String workflowName;
    @OneToMany(mappedBy = "stepId")
    List<WorkflowStep> workflowStep;
    String instanceStatus;
    String allowedRoles;
    Date entryDate;
    String billId;
    double openAmount;

}
