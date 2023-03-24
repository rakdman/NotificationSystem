package com.debitnotification.springserver.instance;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


@Service
@Slf4j
@AllArgsConstructor
public class InstanceService {
    InstanceRepo instanceRepo;

    public List<Instance> getAllInstances() {
        List<Instance> intanceList = instanceRepo.findAll();
        intanceList.sort((instance1, instance2) -> instance2.getEntryDate().compareTo(instance1.getEntryDate()));
        return intanceList;
    }

}
