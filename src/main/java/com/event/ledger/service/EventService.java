package com.event.ledger.service;

import com.event.ledger.dto.BalanceResponse;
import com.event.ledger.dto.EventRequest;
import com.event.ledger.entity.EventEntity;
import com.event.ledger.exception.ResourceNotFoundException;
import com.event.ledger.repository.EventRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class EventService {

    private final EventRepository repository;

    public EventService(EventRepository repository) {
        this.repository = repository;
    }

    public EventEntity createEvent(EventRequest request) {

        return repository.findById(request.getEventId())
                .orElseGet(() -> {
                    EventEntity entity = new EventEntity();
                    entity.setEventId(request.getEventId());
                    entity.setAccountId(request.getAccountId());
                    entity.setType(request.getType());
                    entity.setAmount(request.getAmount());
                    entity.setCurrency(request.getCurrency());
                    entity.setEventTimestamp(request.getEventTimestamp());
                    entity.setMetadata(request.getMetadata());

                    return repository.save(entity);
                });
    }

    public EventEntity getEvent(String eventId) {
        return repository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Event not found: " + eventId
                ));
    }

    public List<EventEntity> getEventsByAccount(String accountId) {
        return repository.findByAccountIdOrderByEventTimestampAsc(accountId);
    }

    public BalanceResponse getBalance(String accountId) {

        BigDecimal balance = repository.calculateBalance(accountId);

        return new BalanceResponse(accountId, balance);
    }
}