package com.trace.opencensus;

import io.opencensus.exporter.trace.stackdriver.StackdriverTraceConfiguration;
import io.opencensus.exporter.trace.stackdriver.StackdriverTraceExporter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.IOException;

@SpringBootApplication
public class OpencensusApplication {

	public static void main(String[] args) {
		SpringApplication.run(OpencensusApplication.class, args);
	}

	@Bean
	public static void createAndRegisterGoogleCloudPlatform() throws IOException {
		StackdriverTraceExporter.createAndRegister(
				StackdriverTraceConfiguration.builder().build());
	}
}
