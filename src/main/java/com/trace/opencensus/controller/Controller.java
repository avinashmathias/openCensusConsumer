package com.trace.opencensus.controller;

import io.opencensus.trace.Tracer;
import io.opencensus.trace.Tracing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    private static final Logger log = LoggerFactory.getLogger(Controller.class);

    private static final Tracer tracer = Tracing.getTracer();


    @GetMapping("/hello")
    public ResponseEntity<String> hello(){
        log.info("Inside hello method of controller class");
        tracer.getCurrentSpan().addAnnotation("Tracer:Inside hello method of controller class");
        return ResponseEntity.ok("Hello");
    }
}
