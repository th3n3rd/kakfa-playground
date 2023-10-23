package com.example.kafka.ecommerce;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("kafka-events")
@EmbeddedKafka
@SpringBootTest(
    webEnvironment = RANDOM_PORT,
    properties = {
        "logging.level.org.apache.kafka=WARN",
        "logging.level.org.apache.zookeeper=WARN",
        "logging.level.kafka.*=WARN",
        "logging.level.org.springframework.kafka.*=WARN",
        "logging.level.state.change.logger=WARN",
        "spring.kafka.producer.value-serializer=org.springframework.kafka.support.serializer.JsonSerializer",
        "spring.kafka.consumer.value-deserializer=org.springframework.kafka.support.serializer.JsonDeserializer",
        "spring.kafka.consumer.properties.spring.json.trusted.packages=com.example.kafka.ecommerce"
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

