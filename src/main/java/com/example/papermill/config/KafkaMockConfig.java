package com.example.papermill.config;

import org.springframework.context.annotation.Configuration;

/**
 * Placeholder configuration class for Kafka.  In a production deployment you
 * would configure a KafkaTemplate bean here to publish messages to an
 * external broker.  In this sample project the OrderEventPublisher simply
 * logs messages to the console, so no beans are defined.
 */
@Configuration
public class KafkaMockConfig {
    // Intentionally left empty to highlight that no Kafka beans are required
    // for this demo.  The OrderEventPublisher logs events instead of
    // publishing them to a broker.
}