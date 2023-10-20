package com.example.kafka.ecommerce;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
class FindOrderDetailsApi {

    @GetMapping("/orders/{orderId}")
    Response handle(@PathVariable UUID orderId) {
        return new Response("Delivered", List.of("usb-c charger"));
    }

    record Response(String state, List<String> items) {}
}
