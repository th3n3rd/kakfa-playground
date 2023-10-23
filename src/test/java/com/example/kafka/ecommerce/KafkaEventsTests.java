package com.example.kafka.ecommerce;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import java.util.concurrent.CompletableFuture;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.kafka.core.KafkaOperations;
import org.springframework.kafka.support.SendResult;

@ExtendWith({MockitoExtension.class, OutputCaptureExtension.class})
class KafkaEventsTests {

    @Mock
    private KafkaOperations<String, Object> kafka;

    @InjectMocks
    private KafkaEvents events;

    @Test
    void logsAnErrorIfEventsFailToBePublished(CapturedOutput output) {
        givenKafkaOperationsFail();
        events.publish("dont-care");
        assertThat(output).contains("Failed to publish the given event");
    }

    void givenKafkaOperationsFail() {
        var failingFuture = new CompletableFuture<SendResult<String, Object>>();
        failingFuture.completeExceptionally(new RuntimeException());
        given(kafka.send(any(), any())).willReturn(failingFuture);
    }
}
