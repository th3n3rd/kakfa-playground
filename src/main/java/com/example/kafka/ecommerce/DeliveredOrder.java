package com.example.kafka.ecommerce;

import java.util.List;
import java.util.UUID;

record DeliveredOrder(UUID orderId, List<String> items) implements Order {

    @Override
    public State state() {
        return State.Delivered;
    }
}
