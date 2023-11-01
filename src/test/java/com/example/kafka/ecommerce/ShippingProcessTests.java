package com.example.kafka.ecommerce;

import static org.mockito.BDDMockito.then;

import com.example.kafka.ecommerce.checkout.OrderPlaced;
import com.example.kafka.ecommerce.common.Events;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class ShippingProcessTests {

    @Autowired
    private Events events;

    @MockBean
    private PrepareShipment prepareShipment;

    @MockBean
    private DispatchShipment dispatchShipment;

    @Nested
    class OnOrderPlaced {
        @Test
        void preparesTheShipment() {
            var orderId = UUID.randomUUID();
            events.publish(new OrderPlaced(orderId, List.of("first-item", "second-item")));

            then(prepareShipment).should().handle(new PrepareShipment.Command(orderId));
        }
    }

    @Nested
    class OnShipmentPrepared {
        @Test
        void dispatchesTheShipment() {
            var orderId = UUID.randomUUID();
            events.publish(new ShipmentPrepared(orderId));

            then(dispatchShipment).should().handle(new DispatchShipment.Command(orderId));
        }
    }
}

