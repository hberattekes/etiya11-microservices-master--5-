package com.etiya.orderservice.repositories;

import com.etiya.orderservice.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Data access layer. In-memory implementation backed by a list; swap for a JPA repository later
 * without touching the business layer.
 */
public interface OrderRepository extends JpaRepository<Order, Integer> {


}
