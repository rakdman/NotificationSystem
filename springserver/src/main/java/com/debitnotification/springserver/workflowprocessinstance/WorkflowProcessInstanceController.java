package com.debitnotification.springserver.workflowprocessinstance;

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
public class WorkflowProcessInstanceController {

    WorkflowProcessInstanceService workflowProcessInstanceService;


    @GetMapping("/readallinstances")
    public ResponseEntity<List<WorkflowProcessInstance>> readAllInstances() {
        return ResponseEntity.ok(workflowProcessInstanceService.getAllInstances());
    }

    @GetMapping(value = "/readoneinstancesbyparams", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<WorkflowProcessInstance>> readInstances(@RequestParam(required = false) String firstName, @RequestParam(required = false) String lastName, @RequestParam(required = false) String contactNo, @RequestParam(required = false) String emailId) {
        return ResponseEntity.ok(workflowProcessInstanceService.getInstancesByParameters(firstName, lastName, contactNo, emailId));
    }

    @GetMapping(value = "/readoneinstancebyid", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WorkflowProcessInstance> readInstanceById(@RequestParam Integer instanceId) {
        return ResponseEntity.ok(workflowProcessInstanceService.getInstancesById(instanceId));
    }


    @PostMapping("/loadfile")
    public void loadFile() throws IOException {
        workflowProcessInstanceService.loadDataFile();
    }

    @PostMapping("/loadpaymentfile")
    public void loadPaymentFile() throws IOException {
        workflowProcessInstanceService.loadPaymentFile();
    }

}
