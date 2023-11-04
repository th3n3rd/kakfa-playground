package com.example.kafka.ecommerce.order;

import com.example.kafka.ecommerce.checkout.OrderPlaced;
import com.example.kafka.ecommerce.shipping.ShipmentDelivered;
import com.example.kafka.ecommerce.shipping.ShipmentDispatched;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
class OrderingProcess {

    private final Orders orders;

    OrderingProcess(Orders orders) {
        this.orders = orders;
    }

    @EventListener
    public void on(OrderPlaced event) {
        orders.save(new PlacedOrder(event.orderId(), event.items()));
    }

    @EventListener
    public void on(ShipmentDispatched event) {
        var placedOrder = orders.findById(event.orderId()).map(it -> (PlacedOrder) it).orElseThrow();
        var dispatchedOrder = placedOrder.asDispatched();
        orders.save(dispatchedOrder);
    }

    @EventListener
    public void on(ShipmentDelivered event) {
        var dispatchedOrder = orders.findById(event.orderId()).map(it -> (DispatchedOrder) it).orElseThrow();
        var deliveredOrder = dispatchedOrder.asDelivered();
        orders.save(deliveredOrder);
    }
}
