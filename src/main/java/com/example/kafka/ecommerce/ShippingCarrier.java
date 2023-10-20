package com.example.kafka.ecommerce;

import java.util.UUID;

interface ShippingCarrier {
    void requestDelivery(UUID orderID);
}
