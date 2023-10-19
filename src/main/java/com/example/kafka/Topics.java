package com.example.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
class Topics {
    public static final String Events = "events";

    @Bean
    NewTopic eventsTopic() {
        return TopicBuilder.name(Topics.Events)
            .partitions(1)
            .replicas(1)
            .build();
    }
}
