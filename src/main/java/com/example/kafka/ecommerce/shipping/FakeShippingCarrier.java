package com.example.kafka.ecommerce.shipping;

import static java.util.concurrent.TimeUnit.SECONDS;

import com.example.kafka.ecommerce.common.Events;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import org.springframework.stereotype.Component;

@Component
class FakeShippingCarrier implements ShippingCarrier {

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private final Events events;

    FakeShippingCarrier(Events events) {
        this.events = events;
    }

    @Override
    public void requestDelivery(UUID orderId) {
        scheduler.schedule(() -> events.publish(new ShipmentDelivered(orderId)), 1, SECONDS);
    }
}
