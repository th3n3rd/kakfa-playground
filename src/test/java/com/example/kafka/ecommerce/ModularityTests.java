package com.example.kafka.ecommerce;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.modulith.docs.Documenter;

class ModularityTests {

    private ApplicationModules modules = ApplicationModules.of(EcommerceApplication.class);

    @Test
    void verifyModularity() {
        modules.forEach(System.out::println);
        modules.verify();
    }

    @Tag("docs")
    @Test
    void documentsModularity() {
        new Documenter(modules).writeDocumentation();
    }
}
