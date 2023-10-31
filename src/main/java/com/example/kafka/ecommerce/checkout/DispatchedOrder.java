package com.example.kafka.ecommerce.checkout;

import java.util.List;
import java.util.UUID;

record DispatchedOrder(UUID orderId, List<String> items) implements Order {

    @Override
    public State state() {
        return State.Dispatched;
    }

    public DeliveredOrder markAsDelivered() {
        return new DeliveredOrder(orderId, items);
    }
}

