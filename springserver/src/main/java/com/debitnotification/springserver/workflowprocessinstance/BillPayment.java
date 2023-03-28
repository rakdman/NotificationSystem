package com.debitnotification.springserver.workflowprocessinstance;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class Payment {
    private String billId;
    private String paymentAmount;

}
