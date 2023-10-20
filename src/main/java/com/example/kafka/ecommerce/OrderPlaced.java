package com.example.kafka.ecommerce;

import java.util.List;
import java.util.UUID;

record OrderPlaced(UUID orderId, List<String> items) {}
