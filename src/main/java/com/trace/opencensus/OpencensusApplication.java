package com.trace.opencensus;

import com.trace.opencensus.controller.Controller;
import io.opencensus.common.Scope;
import io.opencensus.exporter.trace.stackdriver.StackdriverTraceConfiguration;
import io.opencensus.exporter.trace.stackdriver.StackdriverTraceExporter;
import io.opencensus.trace.*;
import io.opencensus.trace.config.TraceConfig;
import io.opencensus.trace.config.TraceParams;
import io.opencensus.trace.samplers.Samplers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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

//	@Bean
//	public static void doWorkFullSampled() {
//		try (Scope ss =
//					 tracer
//							 .spanBuilder("MyChildWorkSpan")
//							 .setSampler(Samplers.alwaysSample())
//							 .startScopedSpan()){
//			tracer.getCurrentSpan().addAnnotation("Tracer:Setting up full span");
//		}
//	}

	@Bean
	public static void initialWork(){
		TraceConfig traceConfig = Tracing.getTraceConfig();
		TraceParams activeTraceParams = traceConfig.getActiveTraceParams();
		traceConfig.updateActiveTraceParams(activeTraceParams.toBuilder().setSampler(Samplers.alwaysSample()).build());

		// 3. Get the global singleton Tracer object.
		Tracer tracer = Tracing.getTracer();

		// 4. Create a scoped span, a scoped span will automatically end when closed.
		// It implements AutoClosable, so it'll be closed when the try block ends.
		try (Scope scope = tracer.spanBuilder("main").startScopedSpan()) {
			System.out.println("About to do some busy work...");
			for (int i = 0; i < 10; i++) {
				doWork(i);
			}
		}

		// 5. Gracefully shutdown the exporter, so that it'll flush queued traces to Zipkin.
		Tracing.getExportComponent().shutdown();
	}

	private static void doWork(int i) {
		// 6. Get the global singleton Tracer object.
		Tracer tracer = Tracing.getTracer();

		// 7. Start another span. If another span was already started, it'll use that span as the parent span.
		// In this example, the main method already started a span, so that'll be the parent span, and this will be
		// a child span.
		try (Scope scope = tracer.spanBuilder("doWork").startScopedSpan()) {
			// Simulate some work.
			Span span = tracer.getCurrentSpan();

			try {
				System.out.println("doing busy work");
				Thread.sleep(100L);
			}
			catch (InterruptedException e) {
				// 6. Set status upon error
				span.setStatus(Status.INTERNAL.withDescription(e.toString()));
			}

			// 7. Annotate our span to capture metadata about our operation
			Map<String, AttributeValue> attributes = new HashMap<>();
			attributes.put("use", AttributeValue.stringAttributeValue("demo"));
			span.addAnnotation("Invoking doWork", attributes);
		}
	}

}
