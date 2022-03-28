package com.trace.opencensus;

import com.google.cloud.opentelemetry.trace.TraceExporter;
import com.trace.opencensus.controller.Controller;
import io.opentelemetry.sdk.OpenTelemetrySdk;
import io.opentelemetry.sdk.trace.SdkTracerProvider;
import io.opentelemetry.sdk.trace.export.BatchSpanProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.IOException;

@SpringBootApplication
public class OpencensusApplication {

    private static final Logger log = LoggerFactory.getLogger(Controller.class);

	TraceExporter traceExporter = TraceExporter.createWithDefaultConfiguration();

	public OpencensusApplication() throws IOException {
	}

	public static void main(String[] args) {
        SpringApplication.run(OpencensusApplication.class, args);

    }

    @Bean
	public void setTraceProvider(){
		OpenTelemetrySdk.builder()
				.setTracerProvider(
						SdkTracerProvider.builder()
								.addSpanProcessor(BatchSpanProcessor.builder(traceExporter).build())
								.build())
				.buildAndRegisterGlobal();
	}


}
