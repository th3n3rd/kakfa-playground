package com.example.kafka.ecommerce.checkout;

import com.example.kafka.ecommerce.ShipmentDelivered;
import com.example.kafka.ecommerce.ShipmentDispatched;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
class OrderingProcess {

    private final Orders orders;

    OrderingProcess(Orders orders) {
        this.orders = orders;
    }

    @EventListener
    public void on(ShipmentDispatched event) {
        var placedOrder = orders.findById(event.orderId()).map(it -> (PlacedOrder) it).orElseThrow();
        var dispatchedOrder = placedOrder.dispatch();
        orders.save(dispatchedOrder);
    }

    @EventListener
    public void on(ShipmentDelivered event) {
        var dispatchedOrder = orders.findById(event.orderId()).map(it -> (DispatchedOrder) it).orElseThrow();
        var deliveredOrder = dispatchedOrder.markAsDelivered();
        orders.save(deliveredOrder);
    }
}
