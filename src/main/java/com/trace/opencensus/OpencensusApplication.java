package com.trace.opencensus;

import com.trace.opencensus.controller.Controller;
import io.opencensus.common.Scope;
import io.opencensus.exporter.trace.stackdriver.StackdriverTraceConfiguration;
import io.opencensus.exporter.trace.stackdriver.StackdriverTraceExporter;
import io.opencensus.trace.Tracer;
import io.opencensus.trace.Tracing;
import io.opencensus.trace.samplers.Samplers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.IOException;

@SpringBootApplication
public class OpencensusApplication {

	private static final Logger log = LoggerFactory.getLogger(Controller.class);

	private static final Tracer tracer = Tracing.getTracer();


	public static void main(String[] args) {
		SpringApplication.run(OpencensusApplication.class, args);
	}

	@Bean
	public static void createAndRegisterGoogleCloudPlatform() throws IOException {
		log.info("Inside createAndRegisterGoogleCloudPlatform method of application class");
		StackdriverTraceExporter.createAndRegister(
				StackdriverTraceConfiguration.builder().build());
	}

	@Bean
	public static void doWorkFullSampled() {
		try (Scope ss =
					 tracer
							 .spanBuilder("MyChildWorkSpan")
							 .setSampler(Samplers.alwaysSample())
							 .startScopedSpan()){
			tracer.getCurrentSpan().addAnnotation("Tracer:Setting up full span");
		}
	}
}
