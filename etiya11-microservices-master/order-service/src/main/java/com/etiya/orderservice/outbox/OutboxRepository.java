package com.etiya.orderservice.outbox;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data JPA access to the outbox table.
 */
public interface OutboxRepository extends JpaRepository<OutboxEvent, Long> {

}
