package com.rodtech.pocspringkafka.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rodtech.pocspringkafka.model.Event;
import com.rodtech.pocspringkafka.producer.EventProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("v1/events")
public class EventController {

    private static Logger log = LoggerFactory.getLogger(EventController.class);

    private final EventProducer eventProducer;

    public EventController(EventProducer eventProducer) {
        this.eventProducer = eventProducer;
    }

    @PostMapping
    public ResponseEntity<Event> process(@RequestBody Event event) throws JsonProcessingException {
        log.info("processing event msg {} ", event);
        eventProducer.persist(UUID.randomUUID().toString(), event);
        log.info("msg processed");
        return ResponseEntity.ok(event);
    }
}
