package com.rodtech.pocspringkafka.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rodtech.pocspringkafka.model.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.time.LocalDate;

@Component
public class EventProducer {

    private static Logger log = LoggerFactory.getLogger(EventProducer.class);
    private final KafkaTemplate<String, String> template;

    @Value("${broken.topics.events-topic}")
    private String topicName;

    public EventProducer(KafkaTemplate<String, String> template) {
        this.template = template;
    }

    public void persist(String messageId, Event event) throws JsonProcessingException {
        sendEventMessage(messageId, event);
    }

    private void sendEventMessage(String messageId, Event event) throws JsonProcessingException {
        String payload = new ObjectMapper().writeValueAsString(event);
        Message<String> message = createMessageWithHeaders(messageId, payload, topicName);

        ListenableFuture<SendResult<String, String>> future = template.send(message);

        future.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onSuccess(SendResult<String, String> result) {
                log.info("sent messge = {} with offset {} ", message, result.getRecordMetadata().offset());
            }
            @Override
            public void onFailure(Throwable ex) {
                log.error("failed to sent message: {} ", ex.getMessage());
            }
        });

    }

    private Message<String> createMessageWithHeaders(String messageId, String payload, String topic) {
        return MessageBuilder.withPayload(payload)
                .setHeader("hash", payload.hashCode())
                .setHeader("version", "1.0.0")
                .setHeader("endOfLife", LocalDate.now().plusDays(1L))
                .setHeader("type", "fct")
                .setHeader("cid", messageId)
                .setHeader(KafkaHeaders.TOPIC, topic)
                .setHeader(KafkaHeaders.MESSAGE_KEY, messageId)
                .build();
    }


}
