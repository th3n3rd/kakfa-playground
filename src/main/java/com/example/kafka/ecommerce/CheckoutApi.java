package com.example.kafka.ecommerce;

import java.util.UUID;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class CheckoutApi {

    @PostMapping("/checkout")
    Response handle() {
        return new Response(UUID.randomUUID());
    }

    record Response(UUID orderId) {}
}
