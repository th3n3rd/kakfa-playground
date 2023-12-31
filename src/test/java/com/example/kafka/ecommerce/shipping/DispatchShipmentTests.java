package com.example.kafka.ecommerce.shipping;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

import com.example.kafka.ecommerce.common.InMemoryEvents;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class DispatchShipmentTests {

    private final ShippingCarrier carrier = mock(ShippingCarrier.class);
    private final InMemoryEvents events = new InMemoryEvents();
    private final DispatchShipment useCase = new DispatchShipment(events, carrier);

    @Test
    void publishAnEventWhenAShipmentIsDispatched() {
        var orderId = UUID.randomUUID();
        useCase.handle(new DispatchShipment.Command(orderId));

        assertThat(events.findAll()).contains(new ShipmentDispatched(orderId));
    }

    @Test
    void requestsNewDelivery() {
        var orderId = UUID.randomUUID();
        useCase.handle(new DispatchShipment.Command(orderId));

        // TODO: would make more sense to return a delivery label and store it somewhere
        then(carrier).should().requestDelivery(orderId);
    }
}
