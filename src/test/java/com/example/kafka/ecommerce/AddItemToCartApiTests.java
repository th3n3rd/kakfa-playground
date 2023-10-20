package com.example.kafka.ecommerce;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(AddItemToCartApi.class)
@Import({ShoppingCart.class, AddItemToCart.class})
class AddItemToCartApiTests {

    @Autowired
    MockMvc client;

    @Autowired
    private ShoppingCart cart;

    @Test
    void addItemToEmptyCartReturnsNoContent() throws Exception {
        var response = client.perform(
            post("/cart")
                .contentType("application/json")
                .content("""
                {
                    "name": "some-item-name"
                }
                """)
        );
        response.andExpect(status().isNoContent());
        assertThat(cart.isEmpty()).isFalse();
    }
}
