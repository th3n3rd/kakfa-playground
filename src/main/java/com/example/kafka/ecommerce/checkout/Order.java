package com.example.kafka.ecommerce.checkout;

import java.util.List;
import java.util.UUID;

record Order(UUID orderId, List<String> items) {}
