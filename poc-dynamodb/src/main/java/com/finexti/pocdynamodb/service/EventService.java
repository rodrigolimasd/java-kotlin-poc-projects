package com.finexti.pocdynamodb.service;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.finexti.pocdynamodb.domain.Event;
import com.finexti.pocdynamodb.dto.EventCreatDto;
import com.finexti.pocdynamodb.dto.EventDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Log4j2
@Service
public class EventService {
    private final DynamoDBMapper dynamoDBMapper;

    public EventService(DynamoDBMapper dynamoDBMapper) {
        this.dynamoDBMapper = dynamoDBMapper;
    }

    public EventCreatDto createNewEvent(EventCreatDto eventCreatDto) {
        Event event = Event.builder()
                .id(eventCreatDto.id())
                .date(eventCreatDto.date())
                .expense(eventCreatDto.expense())
                .income(eventCreatDto.income())
                .note(eventCreatDto.note())
                .dateEventId("%s#%s".formatted(eventCreatDto.date().toString(), UUID.randomUUID().toString()))
                .createDate(LocalDateTime.now())
                .build();
        log.info("creating event: {}", event);
        calculateBalanceForLastEvent(event);
        calculateAfterBalances(event);
        dynamoDBMapper.save(event);
        log.info("event created: {}", event);
        return eventCreatDto;
    }

    private void calculateBalanceForLastEvent(Event event) {
        Event lastEvent = getLastEvent(event.getId(), event.getDate());
        calculateBalance(lastEvent, event);
    }

    private void calculateBalance(Event lastEvent, Event event) {
        double lastBalance = lastEvent!=null ? lastEvent.getBalance() : 0.0;
        double balance = (lastBalance + event.getIncome()) - event.getExpense();
        event.setBalance(balance);
    }

    private void calculateAfterBalances(Event event) {
        List<Event> events = getAfterEvents(event.getId(), event.getDate());
        Event lastEvent = event;
        if(events.isEmpty())  return;

        for(Event e: events) {
            calculateBalance(lastEvent, e);
            lastEvent = e;
        }
        saveBatch(events);
    }

    private void saveBatch(List<Event> events) {
        dynamoDBMapper.batchSave(events);
    }

    private Event getLastEvent(String eventId, LocalDate maxDate) {
        HashMap<String, AttributeValue> filter = new HashMap<>();
        filter.put(":eventId", new AttributeValue().withS(eventId));
        filter.put(":maxDate", new AttributeValue().withS(maxDate.toString()));

        DynamoDBQueryExpression<Event> queryExpression = new DynamoDBQueryExpression<Event>()
                .withKeyConditionExpression("id = :eventId and date_event_id <= :maxDate")
                .withExpressionAttributeValues(filter)
                .withScanIndexForward(false)
                .withLimit(1);

        List<Event> result = dynamoDBMapper.query(Event.class, queryExpression);
        if(result.isEmpty()) {
            return null;
        }
        return result.get(0);
    }

    private List<Event> getAfterEvents(String eventId, LocalDate starDate) {
        HashMap<String, AttributeValue> filter = new HashMap<>();
        filter.put(":eventId", new AttributeValue().withS(eventId));
        filter.put(":starDate", new AttributeValue().withS(starDate.toString()));

        DynamoDBQueryExpression<Event> queryExpression = new DynamoDBQueryExpression<Event>()
                .withKeyConditionExpression("id = :eventId and date_event_id > :starDate")
                .withExpressionAttributeValues(filter);

        return dynamoDBMapper.query(Event.class, queryExpression);
    }

    public List<Event> getAllEvents() {
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
        log.info("scan expression: {}", scanExpression);
        List<Event> events = dynamoDBMapper.scan(Event.class, scanExpression);
        log.info("events scanned: total events: {}", events.size());
        return events;
    }

    public List<EventCreatDto> createNewEvents(List<EventCreatDto> eventCreatDtos) {
        return eventCreatDtos.stream()
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
                        p.getBalance(),
                        p.getNote()))
                .toList();
    }
}
