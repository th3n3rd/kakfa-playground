package com.example.kafka.ecommerce;

import java.util.List;
import java.util.UUID;

interface Order {
    UUID orderId();
    State state();
    List<String> items();

    enum State {
        Placed
    }
}
