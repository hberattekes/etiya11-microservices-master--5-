package com.etiya.productservice.inbox;

import org.springframework.stereotype.Service;

import java.time.Instant;

/**
 * Read/write access to the Inbox table, used by inbound event consumers to make processing
 * idempotent under at-least-once delivery.
 */
@Service
public class InboxService {

    private final InboxEventRepository inboxEventRepository;

    public InboxService(InboxEventRepository inboxEventRepository) {
        this.inboxEventRepository = inboxEventRepository;
    }

    /**
     * @return true if {@code eventId} has already been recorded, i.e. this delivery is a duplicate
     */
    public boolean isAlreadyProcessed(String eventId) {
        return inboxEventRepository.existsByEventId(eventId);
    }

    /**
     * Records {@code eventId} as processed. Call this in the same transaction as the business
     * logic it guards, so both commit or roll back together.
     */
    public void markProcessed(String eventId, String eventType) {
        inboxEventRepository.save(new InboxEvent(eventId, eventType));
    }
}
