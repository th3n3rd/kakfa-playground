package com.example.kafka.ecommerce;

import com.example.kafka.DemoApplication;
import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;

class ModularityTests {

    private ApplicationModules modules = ApplicationModules.of(DemoApplication.class);

    @Test
    void verifyModularity() {
        modules.forEach(System.out::println);
        modules.verify();
    }

}
