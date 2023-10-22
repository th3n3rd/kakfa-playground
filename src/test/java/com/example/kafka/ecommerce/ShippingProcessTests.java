package com.example.kafka.ecommerce;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.event.ApplicationEvents;
import org.springframework.test.context.event.RecordApplicationEvents;

@EmbeddedKafka
@SpringBootTest(
    properties = {
        "logging.level.org.apache.kafka=OFF",
        "logging.level.org.apache.zookeeper=OFF",
        "logging.level.kafka.*=OFF",
        "logging.level.org.springframework.kafka.*=OFF",
        "logging.level.state.change.logger=OFF",
    }
)
class ShippingProcessTests {

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    private Orders orders;

    @Test
    void whenAnOrderIsPlacedIsAutomaticallyDispatched() {
        var orderId = UUID.randomUUID();
        orders.save(new PlacedOrder(orderId, List.of("first-item", "second-item")));

        eventPublisher.publishEvent(new OrderPlaced(orderId, List.of("first-item", "second-item")));

        var dispatchedOrder = orders.findById(orderId).orElse(null);
        assertThat(dispatchedOrder).isNotNull();
        assertThat(dispatchedOrder.state()).isEqualTo(Order.State.Dispatched);
    }

    @Test
    void whenAndOrderIsDeliveredItMarksTheOrderAsSuch() {
        var orderId = UUID.randomUUID();
        orders.save(new DispatchedOrder(orderId, List.of("first-item", "second-item")));

        eventPublisher.publishEvent(new OrderDelivered(orderId));

        var deliveredOrder = orders.findById(orderId).orElse(null);
        assertThat(deliveredOrder).isNotNull();
        assertThat(deliveredOrder.state()).isEqualTo(Order.State.Delivered);
    }
}

