package com.example.kafka.ecommerce;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
class ShippingProcess {

    private final DispatchOrder dispatchOrder;
    private final MarkOrderAsDelivered markOrderAsDelivered;

    ShippingProcess(DispatchOrder dispatchOrder, MarkOrderAsDelivered markOrderAsDelivered) {
        this.dispatchOrder = dispatchOrder;
        this.markOrderAsDelivered = markOrderAsDelivered;
    }

    @EventListener
    public void on(OrderPlaced event) {
        dispatchOrder.handle(new DispatchOrder.Command(event.orderId()));
    }

    @EventListener
    public void on(OrderDelivered event) {
        markOrderAsDelivered.handle(new MarkOrderAsDelivered.Command(event.orderId()));
    }
}
