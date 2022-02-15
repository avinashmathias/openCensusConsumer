package com.trace.opencensus.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    private static final Logger log = LoggerFactory.getLogger(Controller.class);


    @GetMapping("/hello")
    public ResponseEntity<String> hello(){
        log.info("Inside the hello controller class of OpenCensus Consumer application");
        return ResponseEntity.ok("Hello");
    }
}
