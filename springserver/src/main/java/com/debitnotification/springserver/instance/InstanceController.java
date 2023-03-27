package com.debitnotification.springserver.instance;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/instances")
@CrossOrigin
@Slf4j
@AllArgsConstructor
public class InstanceController {

    InstanceService instanceService;


    @GetMapping("/readallinstances")
    public ResponseEntity<List<Instance>> readAllInstances() {
        return ResponseEntity.ok(instanceService.getAllInstances());
    }

    @GetMapping(value = "/readoneinstancesbyparams", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Instance>> readInstances(@RequestParam(required = false) String firstName, @RequestParam(required = false) String lastName, @RequestParam(required = false) String contactNo, @RequestParam(required = false) String emailId) {
        return ResponseEntity.ok(instanceService.getInstancesByParameters(firstName, lastName, contactNo, emailId));
    }

    @GetMapping(value = "/readoneinstancebyid", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Instance> readInstanceById(@RequestParam Integer instanceId) {
        return ResponseEntity.ok(instanceService.getInstancesById(instanceId));
    }


    @PostMapping("/loadfile")
    public void loadFile() throws IOException {
        instanceService.loadDataFile();
    }

    @PostMapping("/loadpaymentfile")
    public void loadPaymentFile() throws IOException {
        instanceService.loadPaymentFile();
    }

}
