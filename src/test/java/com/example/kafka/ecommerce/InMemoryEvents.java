package com.example.kafka.ecommerce;

import java.util.ArrayList;
import java.util.List;

class InMemoryEvents implements Events {

    private final List<Object> events = new ArrayList<>();

    @Override
    public void publish(Object event) {
        events.add(event);
    }

    public List<Object> findAll() {
        return List.copyOf(events);
    }
}
