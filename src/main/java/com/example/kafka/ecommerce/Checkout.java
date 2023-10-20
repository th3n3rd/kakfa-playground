package com.example.kafka.ecommerce;

import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
class Checkout {

    private final ShoppingCart cart;
    private final Orders orders;

    public Checkout(ShoppingCart cart, Orders orders) {
        this.cart = cart;
        this.orders = orders;
    }

    UUID handle() {
        if (cart.isEmpty()) {
            throw new CheckoutFailed.EmptyCart();
        }
        var order = new PlacedOrder(UUID.randomUUID(), List.copyOf(cart.listItems()));
        orders.add(order);
        cart.clear();
        return order.orderId();
    }
}
