package com.trace.opencensus.controller;

import io.opencensus.common.Scope;
import io.opencensus.trace.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@RestController
public class Controller {

    private static final Logger log = LoggerFactory.getLogger(Controller.class);

//    private static final Tracer tracer = Tracing.getTracer();

    WebClient client = WebClient.create();
//
//    @GetMapping("/hello")
//    public ResponseEntity<String> hello(){
//        log.info("Inside hello method of controller class");
////        tracer.getCurrentSpan().addAnnotation("Tracer:Inside hello method of controller class");
//
//        Tracer tracer = Tracing.getTracer();
//
//        // 7. Start another span. If another span was already started, it'll use that span as the parent span.
//        // In this example, the main method already started a span, so that'll be the parent span, and this will be
//        // a child span.
//        try (Scope scope = tracer.spanBuilder("doWork").startScopedSpan()) {
//            // Simulate some work.
//            Span span = tracer.getCurrentSpan();
//
//            try {
//                System.out.println("doing busy work");
//                Thread.sleep(100L);
//            }
//            catch (InterruptedException e) {
//                // 6. Set status upon error
//                span.setStatus(Status.INTERNAL.withDescription(e.toString()));
//            }
//
//            // 7. Annotate our span to capture metadata about our operation
//            Map<String, AttributeValue> attributes = new HashMap<>();
//            attributes.put("use", AttributeValue.stringAttributeValue("demo"));
//            span.addAnnotation("Invoking doWork", attributes);
//        }
//
//        return ResponseEntity.ok("Hello");
//    }

//    @GetMapping("consume")
//    public ResponseEntity<?> consume(){
//        Boolean success = true;
//        Mono<Boolean> response = client.get()
//                .uri("http://localhost:8082/generate")
//                .exchangeToMono(result ->  result.bodyToMono(Boolean.class));
//        Mono<Boolean> response = client.get()
//                .uri("http://localhost:8082/generate")
//                .exchangeToMono(result ->  {
//                    if(result.statusCode().is2xxSuccessful())
//                        return Mono.just(true);
//                    else
//                        return Mono.just(false);
//                });
//        return ResponseEntity.ok(response.block());
//    }

    @GetMapping("/")
    public ResponseEntity<String> fetch(){
        getRestSampleResponse();
        getFailureResponse();
        postUnsuccessfulLogin();
        return ResponseEntity.ok("Hello World");
    }

    private void postUnsuccessfulLogin() {
        HashMap<String, String> request = new HashMap<>();
        request.put("email", "sydney@fife");
        String sample = client.post()
                .uri("https://reqres.in/api/users/23")
                .body(Mono.just(request), HashMap.class)
                .exchangeToMono(result -> result.bodyToMono(String.class)).block();
        System.out.println("failure login:"+sample);
    }

    private void getFailureResponse() {
        String sample = client.get()
                .uri("https://reqres.in/api/users/23")
                .exchangeToMono(result -> result.bodyToMono(String.class)).block();
        System.out.println("failure:"+sample);
    }

    private void getRestSampleResponse() {
        String sample = client.get()
                .uri("https://reqres.in/api/users?page=2")
                .exchangeToMono(result -> result.bodyToMono(String.class)).block();
        System.out.println("rest-api"+sample);
    }
}
