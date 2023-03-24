package com.debitnotification.springserver.instance;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
