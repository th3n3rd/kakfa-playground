package com.example.kafka.ecommerce.common;

import com.example.kafka.ecommerce.common.Events;
import java.util.ArrayList;
import java.util.List;

public class InMemoryEvents implements Events {

    private final List<Object> events = new ArrayList<>();

    @Override
    public void publish(Object event) {
        events.add(event);
    }

    public List<Object> findAll() {
        return List.copyOf(events);
    }
}
