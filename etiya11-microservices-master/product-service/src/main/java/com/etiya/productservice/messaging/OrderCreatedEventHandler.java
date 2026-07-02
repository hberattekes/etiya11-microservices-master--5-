package com.etiya.productservice.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.etiya.productservice.events.OrderCreatedEvent;
import com.etiya.productservice.inbox.InboxService;
import com.etiya.productservice.services.abstracts.ProductService;

/**
 * Business handling for {@link OrderCreatedEvent}, guarded by the Inbox pattern.
 *
 * <p>{@link #handle} must be invoked through the Spring proxy (i.e. as a call to this bean from
 * another bean, not a same-class method call) for {@link Transactional} to take effect: the inbox
 * dedup check, the business logic and the inbox insert all commit or roll back as one unit.</p>
 */
@Service
public class OrderCreatedEventHandler {

    private static final Logger log = LoggerFactory.getLogger(OrderCreatedEventHandler.class);

    private static final String EVENT_TYPE = "OrderCreated";

    private final InboxService inboxService;
    private final ProductService productService;

    public OrderCreatedEventHandler(InboxService inboxService, ProductService productService) {
        this.inboxService = inboxService;
        this.productService = productService;
    }

    @Transactional
    public void handle(String eventId, OrderCreatedEvent event) {
        if (eventId != null && inboxService.isAlreadyProcessed(eventId)) {
            log.info("OrderCreated event #{} already processed, skipping duplicate delivery -> {}",
                    eventId, event);
            return;
        }

        productService.decreaseStock(event.productId(), event.quantity());
        log.info("OrderCreated event #{} consumed, stock decreased -> {}", eventId, event);

        if (eventId != null) {
            inboxService.markProcessed(eventId, EVENT_TYPE);
        }
    }
}
