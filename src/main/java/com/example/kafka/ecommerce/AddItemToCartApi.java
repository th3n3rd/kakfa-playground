package com.example.kafka.ecommerce;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
class AddItemToCartApi {

    @PostMapping("/cart")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void handle(@RequestBody Request request) {

    }

    record Request(String name) {}
}