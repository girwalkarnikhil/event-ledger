package com.event.ledger.controller;

import com.event.ledger.dto.EventRequest;
import com.event.ledger.entity.EventEntity;
import com.event.ledger.service.EventService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController {

    private final EventService service;

    public EventController(EventService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<EventEntity> createEvent(
            @Valid @RequestBody EventRequest request
    ) {

        boolean exists = false;

        try {
            service.getEvent(request.getEventId());
            exists = true;
        } catch (Exception ignored) {
        }

        EventEntity response = service.createEvent(request);

        return ResponseEntity
                .status(exists ? HttpStatus.OK : HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventEntity> getEvent(@PathVariable String id) {
        return ResponseEntity.ok(service.getEvent(id));
    }

    @GetMapping
    public ResponseEntity<List<EventEntity>> getEventsByAccount(
            @RequestParam String account
    ) {
        return ResponseEntity.ok(service.getEventsByAccount(account));
    }
}