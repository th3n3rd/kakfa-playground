package com.example.kafka.ecommerce.checkout;

import com.example.kafka.ecommerce.common.Events;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
class Checkout {

    private final ShoppingCart cart;
    private final Orders orders;
    private final Events events;

    public Checkout(ShoppingCart cart, Orders orders, Events events) {
        this.cart = cart;
        this.orders = orders;
        this.events = events;
    }

    UUID handle() {
        if (cart.isEmpty()) {
            throw new CheckoutFailed.EmptyCart();
        }
        var order = new Order(UUID.randomUUID(), List.copyOf(cart.listItems()));
        orders.save(order);
        events.publish(new OrderPlaced(order.orderId(), order.items()));
        cart.clear();
        return order.orderId();
    }
}
