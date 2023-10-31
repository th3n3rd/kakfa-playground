package com.example.kafka.ecommerce;

import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;

class ModularityTests {

    private ApplicationModules modules = ApplicationModules.of(EcommerceApplication.class);

    @Test
    void verifyModularity() {
        modules.forEach(System.out::println);
        modules.verify();
    }

}
