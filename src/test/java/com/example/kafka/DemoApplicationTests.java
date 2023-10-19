package com.example.kafka;

import static org.awaitility.Awaitility.await;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;

@EmbeddedKafka
@SpringBootTest(
	properties = {
		"logging.level.org.apache.kafka=OFF",
		"logging.level.org.apache.zookeeper=OFF",
		"logging.level.kafka.*=OFF",
		"logging.level.org.springframework.kafka.*=OFF",
		"logging.level.state.change.logger=OFF",
	}
)
class DemoApplicationTests {

	@Autowired
	private EventConsumer eventConsumer;

	@Autowired
	private EventPublisher eventPublisher;

	@Test
	void contextLoads() {}

	@Test
	void publishesAndConsumesEventsThroughKafka() {
		eventPublisher.publish("SomethingHappened");
		await().until(() -> eventConsumer.hasConsumed());
	}
}
