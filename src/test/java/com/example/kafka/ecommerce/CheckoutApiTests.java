package com.example.kafka.ecommerce;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(CheckoutApi.class)
@Import({Orders.class, ShoppingCart.class, Checkout.class, InMemoryEvents.class})
class CheckoutApiTests {

    @Autowired
    private MockMvc client;

    @Autowired
    private ShoppingCart cart;

    @Autowired
    private Orders orders;

    @BeforeEach
    void setUp() {
        cart.clear();
        orders.clear();
    }

    @Test
    void checkoutReturnsTheIdOfThePlacedOrder() throws Exception {
        cart.addItem("first-item");
        cart.addItem("second-item");
        cart.addItem("third-item");

        var response = client.perform(post("/checkout"));

        var ordersFound = orders.findAll();
        assertThat(ordersFound).hasSize(1);
        response.andExpect(status().isOk());
        response.andExpect(content().json("""
        {
            "orderId": "%s"
        }
        """.formatted(ordersFound.get(0).orderId())));
    }

    @Test
    void checkoutReturnsBadRequestIfTheCartIsEmpty() throws Exception {
        var response = client.perform(post("/checkout"));

        assertThat(orders.findAll()).isEmpty();
        response.andExpect(status().isBadRequest());
    }
}
