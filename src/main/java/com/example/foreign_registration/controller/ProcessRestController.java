package com.example.foreign_registration.controller;

import com.example.foreign_registration.model.app.Client;
import com.example.foreign_registration.model.process.ModelCooperation;
import com.example.foreign_registration.repository.app.ClientRepository;
import com.example.foreign_registration.repository.process.ProcessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import org.springframework.web.bind.annotation.RestController;
import com.example.foreign_registration.model.process.Process;

import java.util.Optional;

@RestController
public class ProcessRestController {

    private ProcessRepository processRepository;
    private ClientRepository clientRepository;

    @Autowired
    public ProcessRestController(ProcessRepository processRepository, ClientRepository clientRepository) {
        this.processRepository = processRepository;
        this.clientRepository = clientRepository;
    }

    @PutMapping("/api/process/{id}/{paramName}/{paramValue}")
    public ResponseEntity<Process> changeProcessDetail(@PathVariable Long id, @PathVariable String paramName, @PathVariable String paramValue) {

        Optional<Process> processOptional = processRepository.findById(id);


        if (processOptional.get() == null || !processOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        switch (paramName) {
            case "clientID":
                Optional<Client> client = clientRepository.findById(Long.valueOf(paramValue));
                if (client.get() != null && client.isPresent()) {
                    processOptional.get().setClient(client.get());
                    processRepository.save(processOptional.get());
                } else {
                    return ResponseEntity.notFound().build();
                }
                break;
            case "clientProductName":
                processOptional.get().setDestined_product_name(paramValue);
                processRepository.save(processOptional.get());
                break;
            case "cooperationName":
                processOptional.get().setModel_cooperation(ModelCooperation.valueOf(paramValue));
                processRepository.save(processOptional.get());
                break;
            default:
                return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(processOptional.get());
    }

}