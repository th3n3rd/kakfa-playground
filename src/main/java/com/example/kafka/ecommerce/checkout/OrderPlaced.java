package com.example.kafka.ecommerce.checkout;

import java.util.List;
import java.util.UUID;
import org.jmolecules.event.annotation.DomainEvent;

@DomainEvent
public record OrderPlaced(UUID orderId, List<String> items) {}
