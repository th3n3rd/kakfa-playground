package com.example.kafka.ecommerce;

import static java.util.concurrent.TimeUnit.SECONDS;

import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
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
        scheduler.schedule(() -> events.publish(new OrderDelivered(orderId)), 3, SECONDS);
    }
}
