package com.example.kafka.ecommerce.checkout;

import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
class MarkOrderAsDispatched {

    private final Orders orders;

    MarkOrderAsDispatched(Orders orders) {
        this.orders = orders;
    }

    record Command(UUID orderId) {}

    void handle(Command command) {
        var placedOrder = orders.findById(command.orderId).map(it -> (PlacedOrder) it).orElseThrow();
        var dispatchedOrder = placedOrder.dispatch();
        orders.save(dispatchedOrder);
    }
}