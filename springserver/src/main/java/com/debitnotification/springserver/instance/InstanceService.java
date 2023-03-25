package com.debitnotification.springserver.instance;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


@Service
@Slf4j
public class InstanceService {
    InstanceRepo instanceRepo;
    @Value("${data.customer.filename}")
    private String fileName;

    public InstanceService(InstanceRepo instanceRepo) {
        this.instanceRepo = instanceRepo;
    }


    public void loadfile() throws IOException {
        ClassPathResource classPathResource = new ClassPathResource(fileName);
        File file = classPathResource.getFile();
        ObjectMapper objectMapper = new ObjectMapper();
        TypeReference<List<Instance>> typeListOfCustomer = new TypeReference<List<Instance>>() {
        };
        List<Instance> listOfCustomer = objectMapper.readValue(file, typeListOfCustomer);
        instanceRepo.saveAll(listOfCustomer);
    }

    public List<Instance> getAllInstances() {
        List<Instance> intanceList = instanceRepo.findAll();
        intanceList.sort((instance1, instance2) -> instance2.getEntryDate().compareTo(instance1.getEntryDate()));
        return intanceList;
    }

    public List<Instance> getInstancesByParameters(String firstName, String lastName, String contactNo, String emailId) {
        List<Instance> instanceList = instanceRepo.findAll();

        if (firstName != null && !firstName.isEmpty())
            instanceList = instanceList.parallelStream().filter(a -> a.getFirstName().equalsIgnoreCase(firstName))
                    .collect(Collectors.toList());
        if (lastName != null && !lastName.isEmpty())
            instanceList = instanceList.parallelStream().filter(a -> a.getLastName().equalsIgnoreCase(lastName))
                    .collect(Collectors.toList());

        if (contactNo != null && !contactNo.isEmpty())
            instanceList = instanceList.parallelStream().filter(a -> a.getContactNo().equalsIgnoreCase(contactNo))
                    .collect(Collectors.toList());

        if (emailId != null && !emailId.isEmpty())
            instanceList = instanceList.parallelStream().filter(a -> a.getEmailId().equalsIgnoreCase(emailId))
                    .collect(Collectors.toList());

        instanceList.sort((instance1, instance2) -> instance2.getEntryDate().compareTo(instance1.getEntryDate()));

        return instanceList;
    }
}

