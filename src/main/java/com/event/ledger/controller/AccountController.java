package com.event.ledger.controller;

import com.event.ledger.dto.BalanceResponse;
import com.event.ledger.service.EventService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final EventService service;

    public AccountController(EventService service) {
        this.service = service;
    }

    @GetMapping("/{accountId}/balance")
    public ResponseEntity<BalanceResponse> getBalance(
            @PathVariable String accountId
    ) {
        return ResponseEntity.ok(service.getBalance(accountId));
    }
}