package com.rodtech.pocspringkafka;

import com.rodtech.pocspringkafka.model.Event;
import com.rodtech.pocspringkafka.producer.EventProducer;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;

import java.util.UUID;

@SpringBootApplication
public class PocSpringKafkaApplication {

	@Autowired
	private EventProducer eventProducer;

	public static void main(String[] args) {
		SpringApplication.run(PocSpringKafkaApplication.class, args);
	}

	@Bean
	public NewTopic topic() {
		return TopicBuilder.name("topic1")
				.partitions(1)
				.replicas(1)
				.build();
	}


	@Bean
	public ApplicationRunner runner() {
		return args -> {
			Event event = new Event("title","desc");
			eventProducer.persist(UUID.randomUUID().toString(), event);
		};
	}

}
