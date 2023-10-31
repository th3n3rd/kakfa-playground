package com.example.kafka.ecommerce.common;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.config.TopicBuilder;

@Profile("kafka-events")
@Configuration
class KafkaConfig {
    public final static String Topic = "ecommerce";

    @Bean
    NewTopic ecommerceTopic() {
        return TopicBuilder.name(Topic)
            .partitions(1)
            .replicas(1)
            .build();
    }

}
