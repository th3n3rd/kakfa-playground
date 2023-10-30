package com.example.kafka.ecommerce;

import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
class PrepareShipment {

    private final Events events;

    PrepareShipment(Events events) {
        this.events = events;
    }

    record Command(UUID orderId) {}

    void handle(Command command) {
        events.publish(new ShipmentPrepared(command.orderId));
    }
}
