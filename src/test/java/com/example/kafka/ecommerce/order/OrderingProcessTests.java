package com.example.kafka.ecommerce.order;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.kafka.ecommerce.checkout.OrderPlaced;
import com.example.kafka.ecommerce.common.Events;
import com.example.kafka.ecommerce.shipping.ShipmentDelivered;
import com.example.kafka.ecommerce.shipping.ShipmentDispatched;
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
    class OnOrderPlaced {
        @Test
        void startTrackingState() {
            var orderId = UUID.randomUUID();
            events.publish(new OrderPlaced(orderId, List.of("first-item", "second-item")));

            var placedOrder = orders.findById(orderId).orElse(null);
            assertThat(placedOrder).isNotNull();
            assertThat(placedOrder.items()).containsOnly("first-item", "second-item");
        }
    }

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

