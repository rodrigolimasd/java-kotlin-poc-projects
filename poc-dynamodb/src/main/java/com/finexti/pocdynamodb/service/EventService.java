package com.finexti.pocdynamodb.service;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.finexti.pocdynamodb.domain.Event;
import com.finexti.pocdynamodb.dto.EventDto;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class EventService {
    private final DynamoDBMapper dynamoDBMapper;

    public EventService(DynamoDBMapper dynamoDBMapper) {
        this.dynamoDBMapper = dynamoDBMapper;
    }

    public EventDto createNewEvent(EventDto eventDto) {
        Event event = new Event();
        event.setDate(eventDto.getDate());
        event.setExpense(eventDto.getExpense());
        event.setIncome(eventDto.getIncome());
        event.setNote(eventDto.getNote());

        String dateEventId = eventDto.getDate().toString() + "#" + UUID.randomUUID().toString();
        event.setDateEventId(dateEventId);

        event.setCreateDate(LocalDateTime.now());

        dynamoDBMapper.save(event);
        return eventDto;
    }

    public List<Event> getAllEvents() {
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
        List<Event> events = dynamoDBMapper.scan(Event.class, scanExpression);
        return events;
    }
}
