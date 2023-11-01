package com.example.kafka.ecommerce.shipping;

import java.util.UUID;

interface ShippingCarrier {
    void requestDelivery(UUID orderID);
}
