package com.example.kafka.ecommerce.checkout;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
class AddItemToCartApi {

    private final AddItemToCart useCase;

    AddItemToCartApi(AddItemToCart useCase) {
        this.useCase = useCase;
    }

    @PostMapping("/cart")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void handle(@RequestBody Request request) {
        useCase.handle(new AddItemToCart.Command(request.name()));
    }

    record Request(String name) {}
}
