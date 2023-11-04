package com.example.kafka.ecommerce.order;

import java.util.List;
import java.util.UUID;

record PlacedOrder(UUID orderId, List<String> items) implements Order {

    public State state() {
        return State.Placed;
    }

    public DispatchedOrder asDispatched() {
        return new DispatchedOrder(orderId, items);
    }
}
