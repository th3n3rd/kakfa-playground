package com.example.kafka.ecommerce;

import com.example.kafka.ecommerce.checkout.OrderPlaced;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
class ShippingProcess {

    private final DispatchShipment dispatchShipment;
    private final PrepareShipment prepareShipment;

    ShippingProcess(PrepareShipment prepareShipment, DispatchShipment dispatchShipment) {
        this.prepareShipment = prepareShipment;
        this.dispatchShipment = dispatchShipment;
    }

    @EventListener
    public void on(OrderPlaced event) {
        prepareShipment.handle(new PrepareShipment.Command(event.orderId()));
    }

    @EventListener
    public void on(ShipmentPrepared event) {
        dispatchShipment.handle(new DispatchShipment.Command(event.orderId()));
    }
}
