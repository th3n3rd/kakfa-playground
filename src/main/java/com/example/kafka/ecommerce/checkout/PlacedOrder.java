package com.example.kafka.ecommerce.checkout;

import java.util.List;
import java.util.UUID;

record PlacedOrder(UUID orderId, List<String> items) implements Order {

    public Order.State state() {
        return Order.State.Placed;
    }

    public DispatchedOrder dispatch() {
        return new DispatchedOrder(orderId, items);
    }
}
