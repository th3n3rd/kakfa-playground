package com.example.kafka.ecommerce;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class AddItemToCartTests {

    @Test
    void addItemToEmptyCart() {
        var cart = new ShoppingCart();
        var useCase = new AddItemToCart(cart);
        useCase.handle(new AddItemToCart.Command("any-item-name"));
        assertThat(cart.isEmpty()).isFalse();
    }

    @Test
    void addMoreThanOneItemToCart() {
        var cart = new ShoppingCart();
        var useCase = new AddItemToCart(cart);
        useCase.handle(new AddItemToCart.Command("first-item"));
        useCase.handle(new AddItemToCart.Command("second-item"));
        assertThat(cart.listItems()).contains("first-item", "second-item");
    }
}

