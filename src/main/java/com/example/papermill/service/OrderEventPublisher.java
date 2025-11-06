package com.example.papermill.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Publishes an event when a new order is created.  In a production system
 * this component would use a KafkaTemplate to send the message to a
 * configured broker.  In this sample we simply log the event to simulate
 * publishing while keeping the dependency footprint small.
 */
@Component
public class OrderEventPublisher {
    private static final Logger log = LoggerFactory.getLogger(OrderEventPublisher.class);

    /**
     * Publish an order created event.  For demonstration purposes this
     * implementation logs the payload.  In a real application you could
     * inject a KafkaTemplate and publish to a topic.
     *
     * @param orderId the identifier of the new order
     * @param jsonPayload a JSON string describing the order
     */
    public void publishOrderCreated(Long orderId, String jsonPayload) {
        log.info("[MOCK-KAFKA] topic=order-created key={} payload={}", orderId, jsonPayload);
    }
}