package com.example.kafka.ecommerce;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.springframework.http.MediaType.APPLICATION_JSON;

import com.example.kafka.ecommerce.checkout.CheckoutApi;
import com.example.kafka.ecommerce.checkout.FindOrderDetailsApi;
import com.example.kafka.ecommerce.checkout.Order;
import java.util.List;
import java.util.UUID;
import org.assertj.core.api.Assertions;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.RequestEntity;

public class Buyer {
    private final TestRestTemplate client;

    public Buyer(TestRestTemplate client) {
        this.client = client;
    }

    UUID checkout() {
        var request = RequestEntity.post("/checkout").build();
        var response = client.exchange(request, CheckoutApi.Response.class);
        Assertions.assertThat(response.getStatusCode().is2xxSuccessful()).as(response.toString()).isTrue();
        return response.getBody().orderId();
    }

    void addItemToCart(String itemName) {
        var request = RequestEntity
            .post("/cart")
            .contentType(APPLICATION_JSON)
            .body("""
            {
                "name": "%s"
            }
            """.formatted(itemName));
        var response = client.exchange(request, Void.class);
        assertThat(response.getStatusCode().is2xxSuccessful())
            .as(response.toString())
            .isTrue();
    }

    void hasReceivedAllItems(UUID orderId, List<String> expectedItems) {
        await()
            .atMost(5, SECONDS)
            .alias("all items have been received")
            .until(() -> {
               var orderDetails = findOrderDetails(orderId);
               return orderDetails.state().equals(Order.State.Delivered)
                   && orderDetails.items().containsAll(expectedItems);
            });
    }

    private FindOrderDetailsApi.Response findOrderDetails(UUID orderId) {
        var request = RequestEntity
            .get("/orders/{orderId}", orderId)
            .build();
        var response = client.exchange(request, FindOrderDetailsApi.Response.class);
        assertThat(response.getStatusCode().is2xxSuccessful())
            .as(response.toString())
            .isTrue();
        return response.getBody();
    }
}
