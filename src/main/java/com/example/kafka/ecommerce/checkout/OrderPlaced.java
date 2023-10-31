package com.example.kafka.ecommerce.checkout;

import java.util.List;
import java.util.UUID;

public record OrderPlaced(UUID orderId, List<String> items) {}
