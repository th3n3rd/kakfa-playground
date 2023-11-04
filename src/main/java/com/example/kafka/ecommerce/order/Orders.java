package com.example.kafka.ecommerce.order;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
class Orders {
    private final Map<UUID, Order> ordersById = new HashMap<>();

    boolean exists(UUID orderId) {
        return ordersById.containsKey(orderId);
    }

    void save(Order order) {
        ordersById.put(order.orderId(), order);
    }

    public Optional<Order> findById(UUID orderId) {
        return Optional.ofNullable(ordersById.get(orderId));
    }

    public List<Order> findAll() {
        return List.copyOf(ordersById.values());
    }

    public void clear() {
        ordersById.clear();
    }
}
