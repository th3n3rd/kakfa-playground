package com.example.kafka.ecommerce;

import org.springframework.stereotype.Component;

@Component
class AddItemToCart {

    private final ShoppingCart cart;

    AddItemToCart(ShoppingCart cart) {
        this.cart = cart;
    }

    record Command(String itemName) {}

    void handle(Command command) {
        cart.addItem(command.itemName);
    }
}
