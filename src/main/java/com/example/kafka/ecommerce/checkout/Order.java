package com.example.kafka.ecommerce.checkout;

import java.util.List;
import java.util.UUID;

public interface Order {
    UUID orderId();
    State state();
    List<String> items();

    enum State {
        Placed,
        Dispatched,
        Delivered
    }
}
