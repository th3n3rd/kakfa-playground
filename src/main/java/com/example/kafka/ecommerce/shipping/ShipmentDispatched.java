package com.example.kafka.ecommerce.shipping;

import java.util.UUID;
import org.jmolecules.event.annotation.DomainEvent;

@DomainEvent
public record ShipmentDispatched(UUID orderId) {}
