package com.example.kafka.ecommerce;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;

@EmbeddedKafka
@SpringBootTest(
    webEnvironment = RANDOM_PORT,
    properties = {
        "logging.level.org.apache.kafka=OFF",
        "logging.level.org.apache.zookeeper=OFF",
        "logging.level.kafka.*=OFF",
        "logging.level.org.springframework.kafka.*=OFF",
        "logging.level.state.change.logger=OFF",
    }
)
class JourneyTests {

    @Autowired
    private TestRestTemplate client;

    @Test
    void buyerJourney() {
        var buyer = new Buyer(client);
        buyer.addItemToCart("usb-c charger");
        var orderId = buyer.checkout();
        buyer.hasReceivedAllItems(orderId, List.of("usb-c charger"));
    }

}
