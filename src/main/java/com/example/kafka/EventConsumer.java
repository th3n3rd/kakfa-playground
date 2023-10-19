package com.example.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
class EventConsumer {
    private boolean hasConsumed = false;

    @KafkaListener(id = "eventConsumer", topics = Topics.Events)
    void consume(String event) {
        hasConsumed = "SomethingHappened".equals(event);
    }

    Boolean hasConsumed() {
        return hasConsumed;
    }
}
