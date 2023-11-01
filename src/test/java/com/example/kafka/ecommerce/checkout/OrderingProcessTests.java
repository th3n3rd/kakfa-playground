package com.example.kafka.ecommerce.checkout;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.kafka.ecommerce.ShipmentDelivered;
import com.example.kafka.ecommerce.ShipmentDispatched;
import com.example.kafka.ecommerce.common.Events;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class OrderingProcessTests {

    @Autowired
    private Events events;

    @Autowired
    private Orders orders;

    @Nested
    class OnShipmentDispatched {
        @Test
        void marksTheOrderAsDispatched() {
            var orderId = UUID.randomUUID();
            orders.save(new PlacedOrder(orderId, List.of("first-item", "second-item")));

            events.publish(new ShipmentDispatched(orderId));

            var deliveredOrder = orders.findById(orderId).orElse(null);
            assertThat(deliveredOrder).isNotNull();
            assertThat(deliveredOrder.state()).isEqualTo(Order.State.Dispatched);
        }
    }

    @Nested
    class OnShipmentDelivered {
        @Test
        void marksTheOrderAsDelivered() {
            var orderId = UUID.randomUUID();
            orders.save(new DispatchedOrder(orderId, List.of("first-item", "second-item")));

            events.publish(new ShipmentDelivered(orderId));

            var deliveredOrder = orders.findById(orderId).orElse(null);
            assertThat(deliveredOrder).isNotNull();
            assertThat(deliveredOrder.state()).isEqualTo(Order.State.Delivered);
        }
    }
}

