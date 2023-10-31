package com.example.kafka.ecommerce.checkout;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
class ShoppingCart {

    private final List<String> items = new ArrayList<>();

    boolean isEmpty() {
        return items.isEmpty();
    }

    public void addItem(String itemName) {
        items.add(itemName);
    }

    public List<String> listItems() {
        return items;
    }

    public void clear() {
        items.clear();
    }
}
