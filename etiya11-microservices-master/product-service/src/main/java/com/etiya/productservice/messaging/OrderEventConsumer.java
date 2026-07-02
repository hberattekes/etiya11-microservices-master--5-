package com.etiya.productservice.messaging;

import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.etiya.productservice.events.OrderCreatedEvent;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Spring Cloud Stream consumer for the CDC pipeline. The bean name {@code orderCreated} is
 * referenced by {@code spring.cloud.function.definition} and bound to the input binding
 * {@code orderCreated-in-0} (Kafka topic {@code cdc.public.outbox_event}) in application.yml.
 *
 * <p>Debezium ships the raw row as a JSON envelope; the {@code after} object holds the outbox
 * row's columns ({@code event_id}, {@code payload}, ...). Parsing and delegation happen here;
 * dedup + business logic run atomically in {@link OrderCreatedEventHandler}.</p>
 */
@Configuration
public class OrderEventConsumer {

    private static final Logger log = LoggerFactory.getLogger(OrderEventConsumer.class);

    private final OrderCreatedEventHandler orderCreatedEventHandler;
    private final ObjectMapper objectMapper;

    public OrderEventConsumer(OrderCreatedEventHandler orderCreatedEventHandler, ObjectMapper objectMapper) {
        this.orderCreatedEventHandler = orderCreatedEventHandler;
        this.objectMapper = objectMapper;
    }

    @Bean
    public Consumer<String> orderCreated() {
        return rawJson -> {
            try {
                JsonNode after = objectMapper.readTree(rawJson).get("after");
                String eventId = after.get("event_id").asText();
                OrderCreatedEvent event = objectMapper.readValue(after.get("payload").asText(), OrderCreatedEvent.class);

                orderCreatedEventHandler.handle(eventId, event);
            } catch (Exception e) {
                log.error("Failed to process CDC message: {}", rawJson, e);
                throw new RuntimeException(e);
            }
        };
    }
}
