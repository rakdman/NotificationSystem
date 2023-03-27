package com.debitnotification.springserver.instance;

import org.springframework.data.jpa.repository.JpaRepository;

public interface InstanceRepo extends JpaRepository<Instance, Long> {

    Instance findByBillId(String billId);
}
