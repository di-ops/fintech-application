package ru.example.fintech.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.example.fintech.dto.AccountDto;
import ru.example.fintech.service.AccountService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/v1/customers/{id}/accounts")
public class AccountController {


    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public List<AccountDto> findAll(@PathVariable("id") int customerId) {
        return accountService.findAll(customerId);
    }

    @PostMapping
    public AccountDto openNew(@PathVariable("id") int customerId) {
        return accountService.createNew(customerId);
    }

    @GetMapping("/{accountId}")
    public AccountDto findByAccountId(@PathVariable("accountId") int accountId, @PathVariable("id") int customerId) {
        return accountService.findById(accountId, customerId);
    }

}
