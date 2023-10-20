package com.example.kafka.ecommerce;

interface Events {
    void publish(Object event);
}
