package com.example.kafka.ecommerce.checkout;

import com.example.kafka.ecommerce.ShipmentDelivered;
import com.example.kafka.ecommerce.ShipmentDispatched;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
class OrderProcess {

    private final MarkOrderAsDelivered markOrderAsDelivered;
    private final MarkOrderAsDispatched markOrderAsDispatched;

    OrderProcess(MarkOrderAsDelivered markOrderAsDelivered, MarkOrderAsDispatched markOrderAsDispatched) {
        this.markOrderAsDelivered = markOrderAsDelivered;
        this.markOrderAsDispatched = markOrderAsDispatched;
    }

    @EventListener
    public void on(ShipmentDispatched event) {
        markOrderAsDispatched.handle(new MarkOrderAsDispatched.Command(event.orderId()));
    }

    @EventListener
    public void on(ShipmentDelivered event) {
        markOrderAsDelivered.handle(new MarkOrderAsDelivered.Command(event.orderId()));
    }
}