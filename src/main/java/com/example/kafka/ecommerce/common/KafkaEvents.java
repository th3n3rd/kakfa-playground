package com.example.kafka.ecommerce.common;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaOperations;
import org.springframework.stereotype.Component;

@Profile("kafka-events")
@Primary
@Component
class KafkaEvents implements Events {

    private static final Logger logger = LoggerFactory.getLogger(KafkaEvents.class);
    private final KafkaOperations<String, Object> kafka;
    private final ApplicationEventPublisher eventPublisher;

    KafkaEvents(KafkaOperations<String, Object> kafka, ApplicationEventPublisher eventPublisher) {
        this.kafka = kafka;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void publish(Object event) {
        kafka.send(KafkaConfig.Topic, event).whenComplete((result, throwable) -> {
            if (throwable != null) {
                logger.error("Failed to publish the given event");
            }
        });
    }

    @KafkaListener(topics = KafkaConfig.Topic, groupId = "ecommerce")
    void consume(ConsumerRecord<?, ?> event) {
        eventPublisher.publishEvent(event.value());
    }
}
