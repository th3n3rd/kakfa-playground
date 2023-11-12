package com.example.kafka.ecommerce.shipping;

import java.util.UUID;
import org.jmolecules.event.annotation.DomainEvent;

@DomainEvent
record ShipmentPrepared(UUID orderId) {}
