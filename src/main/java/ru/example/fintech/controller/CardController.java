package ru.example.fintech.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.example.fintech.dto.CardDto;
import ru.example.fintech.dto.DepositDto;
import ru.example.fintech.service.CardService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/v1/customers/{id}/accounts/{accountId}/cards")
public class CardController {

    final CardService cardService;

    @Autowired
    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @GetMapping
    public List<CardDto> getAll(@PathVariable("id") int customerId,
                                @PathVariable("accountId") int accountId){
        return cardService.findAllCard(customerId, accountId);
    }

    @PostMapping
    public CardDto create(@PathVariable("accountId") int accountId, @PathVariable("id") int customerId){
        return cardService.createCard(accountId, customerId);
    }

    @GetMapping("/{cardId}")
    public CardDto checkBalance(@PathVariable("id") int customerId,
                                @PathVariable("accountId") int accountId,
                                @PathVariable("cardId") int cardId){
        return cardService.checkBalance(customerId, accountId, cardId);
    }
    @PostMapping("/{cardId}")
    public CardDto makeDeposit(@PathVariable("id") int customerId,
                               @PathVariable("accountId") int accountId,
                               @PathVariable("cardId") int cardId,
                               @RequestBody DepositDto depositDto){
        return cardService.makeDeposit(customerId, accountId, cardId, depositDto);
    }
}
