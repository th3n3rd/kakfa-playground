package com.example.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
class EventPublisher {

    private final KafkaTemplate<String, String> client;

    EventPublisher(KafkaTemplate<String, String> client) {
        this.client = client;
    }

    void publish(String event) {
        client.send(Topics.Events, event);
    }

}
