package com.example.kafka.ecommerce;

import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
class MarkOrderAsDelivered {
    private final Orders orders;

    MarkOrderAsDelivered(Orders orders) {
        this.orders = orders;
    }

    record Command(UUID orderId) {}

    public void handle(Command command) {
        var dispatchedOrder = orders.findById(command.orderId).map(it -> (DispatchedOrder) it).orElseThrow();
        var deliveredOrder = dispatchedOrder.markAsDelivered();
        orders.save(deliveredOrder);
    }
}
