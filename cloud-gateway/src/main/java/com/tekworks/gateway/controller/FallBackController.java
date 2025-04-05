package com.tekworks.gateway.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FallBackController {
 
    @GetMapping("/employeeServiceFallBack")
    public ResponseEntity<String> orderServiceFallBack(){
        String mess="Employee Service is down";
        return new ResponseEntity<>(mess, HttpStatus.SERVICE_UNAVAILABLE);
    }
 
    @GetMapping("/departmentServiceFallBack")
    public ResponseEntity<String> productServiceFallBack(){
        String mesg="department Service is down";
        return new ResponseEntity<>(mesg, HttpStatus.SERVICE_UNAVAILABLE);
    }
 
    @GetMapping("/addressServiceFallBack")
    public ResponseEntity<String> paymentServiceFallBack(){
        String mess="address Service is down";
        return new ResponseEntity<>(mess, HttpStatus.SERVICE_UNAVAILABLE);
    }
}