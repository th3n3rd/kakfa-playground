package com.example.kafka.ecommerce;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class PrepareShipmentTests {

    private final InMemoryEvents events = new InMemoryEvents();
    private final PrepareShipment useCase = new PrepareShipment(events);

    @Test
    void publishAnEventWhenAShipmentHasBeenPrepared() {
        var orderId = UUID.randomUUID();
        useCase.handle(new PrepareShipment.Command(orderId));

        assertThat(events.findAll()).contains(new ShipmentPrepared(orderId));
    }

}
