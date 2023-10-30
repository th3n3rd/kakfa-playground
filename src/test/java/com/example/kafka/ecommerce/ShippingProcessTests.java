package com.example.kafka.ecommerce;

import static org.mockito.BDDMockito.then;

import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationEventPublisher;

@SpringBootTest
class ShippingProcessTests {

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @MockBean
    private PrepareShipment prepareShipment;

    @MockBean
    private DispatchShipment dispatchShipment;

    @Test
    void whenAnOrderIsPlacedAShipmentIsAutomaticallyPrepared() {
        var orderId = UUID.randomUUID();
        eventPublisher.publishEvent(new OrderPlaced(orderId, List.of("first-item", "second-item")));

        then(prepareShipment).should().handle(new PrepareShipment.Command(orderId));
    }

    @Test
    void whenAPackageHasFinishedPreparationIsAutomaticallyDispatched() {
        var orderId = UUID.randomUUID();
        eventPublisher.publishEvent(new ShipmentPrepared(orderId));

        then(dispatchShipment).should().handle(new DispatchShipment.Command(orderId));
    }
}

