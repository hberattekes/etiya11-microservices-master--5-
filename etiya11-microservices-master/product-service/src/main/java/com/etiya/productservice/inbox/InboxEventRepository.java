package com.etiya.productservice.inbox;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA access to the inbox table.
 */
public interface InboxEventRepository extends JpaRepository<InboxEvent, Long> {

    /**
     * Whether an event with this id has already been processed, used to detect redeliveries.
     */
    boolean existsByEventId(String eventId);
}
