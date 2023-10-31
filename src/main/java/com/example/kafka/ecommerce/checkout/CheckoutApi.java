package com.example.kafka.ecommerce.checkout;

import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CheckoutApi {

    private final Checkout useCase;

    CheckoutApi(Checkout useCase) {
        this.useCase = useCase;
    }

    @PostMapping("/checkout")
    Response handle() {
        var newOrderId = useCase.handle();
        return new Response(newOrderId);
    }

    @ExceptionHandler(CheckoutFailed.EmptyCart.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    void checkoutFailedBecauseOfEmptyCart() {}

    public record Response(UUID orderId) {}
}
