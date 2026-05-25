package com.event.ledger.dto;

import com.event.ledger.entity.EventType;

import java.math.BigDecimal;
import java.time.Instant;

public record EventResponse(
        String eventId,
        String accountId,
        EventType type,
        BigDecimal amount,
        String currency,
        Instant eventTimestamp,
        String metadata
) {
}
