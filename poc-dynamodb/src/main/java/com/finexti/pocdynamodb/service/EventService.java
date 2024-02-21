package com.finexti.pocdynamodb.service;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.finexti.pocdynamodb.domain.Event;
import com.finexti.pocdynamodb.dto.EventDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Log4j2
@Service
public class EventService {
    private final DynamoDBMapper dynamoDBMapper;

    public EventService(DynamoDBMapper dynamoDBMapper) {
        this.dynamoDBMapper = dynamoDBMapper;
    }

    public EventDto createNewEvent(EventDto eventDto) {
        Event event = Event.builder()
                .id(eventDto.id())
                .date(eventDto.date())
                .expense(eventDto.expense())
                .income(eventDto.income())
                .note(eventDto.note())
                .dateEventId("%s#%s".formatted(eventDto.date().toString(), UUID.randomUUID().toString()))
                .createDate(LocalDateTime.now())
                .build();
        log.info("creating event: {}", event);
        dynamoDBMapper.save(event);
        log.info("event created: {}", event);
        return eventDto;
    }

    public List<Event> getAllEvents() {
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
        log.info("scan expression: {}", scanExpression);
        List<Event> events = dynamoDBMapper.scan(Event.class, scanExpression);
        log.info("events scanned: total events: {}", events.size());
        return events;
    }

    public List<EventDto> createNewEvents(List<EventDto> eventDtos) {
        return eventDtos.stream()
                .map(this::createNewEvent)
                .toList();
    }

    public List<EventDto> getEventsById(String id) {
        log.info("finding events by id {}", id);
        HashMap<String, AttributeValue> filter = new HashMap<>();
        filter.put(":id", new AttributeValue().withS(id));

        DynamoDBQueryExpression<Event> queryExpression = new DynamoDBQueryExpression<Event>()
                .withKeyConditionExpression("id = :id")
                        .withExpressionAttributeValues(filter);
        List<Event> events = dynamoDBMapper.query(Event.class, queryExpression);

        log.info("events scanned: total events: {}", events.size());
        return events.stream()
                .map(p-> new EventDto(
                        p.getId(),
                        p.getDate(),
                        p.getExpense(),
                        p.getIncome(),
                        p.getNote()))
                .toList();
    }
}
