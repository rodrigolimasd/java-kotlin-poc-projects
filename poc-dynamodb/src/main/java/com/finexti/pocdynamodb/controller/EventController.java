package com.finexti.pocdynamodb.controller;

import com.finexti.pocdynamodb.domain.Event;
import com.finexti.pocdynamodb.dto.EventCreatDto;
import com.finexti.pocdynamodb.dto.EventDto;
import com.finexti.pocdynamodb.service.EventService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/event")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public List<Event> getAllEvents() {
        return eventService.getAllEvents();
    }

    @GetMapping("{id}")
    public List<EventDto> getEventById(@PathVariable String id) {
        return eventService.getEventsById(id);
    }

    @PostMapping
    public EventCreatDto createEvent(@RequestBody EventCreatDto eventCreatDto) {
        return eventService.createNewEvent(eventCreatDto);
    }

    @PostMapping("/batch")
    public List<EventCreatDto> createEvents(@RequestBody List<EventCreatDto> eventCreatDtos) {
        return eventService.createNewEvents(eventCreatDtos);
    }
}
