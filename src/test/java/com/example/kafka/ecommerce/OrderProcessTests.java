package com.example.kafka.ecommerce;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;

@SpringBootTest
class OrderProcessTests {

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    private Orders orders;

    @Test
    void whenTheShipmentIsDispatchedItMarksTheOrderAsDispatched() {
        var orderId = UUID.randomUUID();
        orders.save(new PlacedOrder(orderId, List.of("first-item", "second-item")));

        eventPublisher.publishEvent(new ShipmentDispatched(orderId));

        var deliveredOrder = orders.findById(orderId).orElse(null);
        assertThat(deliveredOrder).isNotNull();
        assertThat(deliveredOrder.state()).isEqualTo(Order.State.Dispatched);
    }

    @Test
    void whenTheShipmentIsDeliveredItMarksTheOrderAsDelivered() {
        var orderId = UUID.randomUUID();
        orders.save(new DispatchedOrder(orderId, List.of("first-item", "second-item")));

        eventPublisher.publishEvent(new ShipmentDelivered(orderId));

        var deliveredOrder = orders.findById(orderId).orElse(null);
        assertThat(deliveredOrder).isNotNull();
        assertThat(deliveredOrder.state()).isEqualTo(Order.State.Delivered);
    }
}

