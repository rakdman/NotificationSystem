package com.debitnotification.springserver.workflowprocessinstance;

import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkflowProcessInstanceRepo extends JpaRepository<WorkflowProcessInstance, Long> {

    WorkflowProcessInstance findByBillId(String billId);
}
