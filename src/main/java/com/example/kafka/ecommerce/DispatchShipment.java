package com.example.kafka.ecommerce;

import java.util.UUID;
import jdk.jfr.Event;
import org.springframework.stereotype.Component;

@Component
class DispatchShipment {

    private final Events events;
    private final ShippingCarrier shippingCarrier;

    DispatchShipment(Events events, ShippingCarrier shippingCarrier) {
        this.events = events;
        this.shippingCarrier = shippingCarrier;
    }

    record Command(UUID orderId) {}

    void handle(Command command) {
        shippingCarrier.requestDelivery(command.orderId);
        events.publish(new ShipmentDispatched(command.orderId));
    }
}
