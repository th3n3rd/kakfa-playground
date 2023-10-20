package com.example.kafka.ecommerce;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(FindOrderDetailsApi.class)
@Import(Orders.class)
class FindOrderDetailsApiTests {

    @Autowired
    private MockMvc client;

    @Autowired
    private Orders orders;

    @Test
    void findAndReturnsTheOrderDetailForTheGivenId() throws Exception {
        var expectedOrder = new PlacedOrder(UUID.randomUUID(), List.of("first-item"));
        orders.save(expectedOrder);

        var response = client.perform(get("/orders/{orderId}", expectedOrder.orderId()));

        response.andExpect(status().isOk());
        response.andExpect(content().json("""
        {
            "orderId": "%s",
            "items": ["first-item"],
            "state": "Placed"
        }
        """.formatted(expectedOrder.orderId())));
    }

}
