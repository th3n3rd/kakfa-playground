package com.example.kafka.ecommerce;

import java.util.List;
import java.util.UUID;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
class FindOrderDetailsApi {

    private final Orders orders;

    FindOrderDetailsApi(Orders orders) {
        this.orders = orders;
    }

    @GetMapping("/orders/{orderId}")
    Response handle(@PathVariable UUID orderId) {
        var order = orders.findById(orderId).orElseThrow();
        return new Response(
            order.orderId(),
            order.items(),
            order.state()
        );
    }

    record Response(UUID orderId, List<String> items, Order.State state) {}
}
