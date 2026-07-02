package com.etiya.productservice.inbox;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.Instant;

/**
 * A record that a given inbound event has already been processed (Inbox pattern).
 *
 * <p>Kafka / Spring Cloud Stream delivery is at-least-once, so the same message can arrive more
 * than once (e.g. a redelivery after a crash before the consumer offset was committed). Before
 * acting on an event, the consumer checks whether its {@link #eventId} is already present here;
 * if so, the event is a duplicate and is skipped. Otherwise the business logic runs and this row
 * is inserted in the same transaction, making the whole operation idempotent.</p>
 */
@Entity
@Table(name = "inbox_events")
public class InboxEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Dedup key: the producer's stable event id (see order-service's OutboxEvent#eventId). */
    @Column(nullable = false, updatable = false, unique = true, length = 36)
    private String eventId;

    /** Logical event name, e.g. {@code "OrderCreated"}. */
    @Column(nullable = false)
    private String eventType;

    @Column(nullable = false)
    private Instant processedAt = Instant.now();

    /** Required by JPA. */
    protected InboxEvent() {
    }

    public InboxEvent(String eventId, String eventType) {
        this.eventId = eventId;
        this.eventType = eventType;
    }

    public Long getId() {
        return id;
    }

    public String getEventId() {
        return eventId;
    }

    public String getEventType() {
        return eventType;
    }

    public Instant getProcessedAt() {
        return processedAt;
    }
}
