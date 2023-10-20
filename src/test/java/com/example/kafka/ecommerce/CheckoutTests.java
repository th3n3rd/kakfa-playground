package com.example.kafka.ecommerce;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.UUID;
import org.junit.jupiter.api.Test;

class CheckoutTests {

    private final ShoppingCart cart = new ShoppingCart();
    private final Orders orders = new Orders();
    private final Checkout useCase = new Checkout(cart, orders);

    @Test
    void placeNewOrderAndReturnOrderId() {
        cart.addItem("first-item");
        cart.addItem("second-item");

        var orderId = useCase.handle();

        var order = orders.findById(orderId).orElse(null);
        assertThat(order).isNotNull();
        assertThat(order.state()).isEqualTo(Order.State.Placed);
    }

    @Test
    void placeNewOrderWithAllItemsFromTheCart() {
        cart.addItem("first-item");
        cart.addItem("second-item");

        var orderId = useCase.handle();

        var order = orders.findById(orderId).orElse(null);
        assertThat(order.items()).contains("first-item", "second-item");
    }

    @Test
    void failsToCheckoutIfCartIsEmpty() {
        assertThrows(CheckoutFailed.EmptyCart.class, useCase::handle);
    }

    @Test
    void placeManyOrders() {
        cart.addItem("first-item");
        var firstOrderId = useCase.handle();

        cart.addItem("second-item");
        var secondOrderId = useCase.handle();

        var nonExistingOrderId = UUID.randomUUID();

        assertThat(orders.exists(firstOrderId)).isTrue();
        assertThat(orders.exists(secondOrderId)).isTrue();
        assertThat(orders.exists(nonExistingOrderId)).isFalse();
    }

    @Test
    void resetsTheShoppingCart() {
        cart.addItem("first-item");
        cart.addItem("second-item");

        useCase.handle();

        assertThat(cart.isEmpty()).isTrue();
    }
}


