package com.debitnotification.springserver.instance;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UniqueElements;

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
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn
    List<WorkflowStep> workflowStep;
    String instanceStatus;
    String allowedRoles;
    Date entryDate = new Date();
    
    String billId;
    double openAmount;

}
