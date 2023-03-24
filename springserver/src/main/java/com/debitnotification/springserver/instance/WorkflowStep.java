package com.debitnotification.springserver.instance;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkflowStep {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long stepId;
    private String stepName;
    private Date stepScheduleDate;
    private String stepStatus;
    private Date executionDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "instanceId")
    private Instance instance;


}