package ru.example.fintech.service;

import ru.example.fintech.dto.AccountDto;

import java.util.List;

/**
 * AccountService contains validation and logic that can works with different dao-layers
 */
public interface AccountService {
    /**
     *
     * @param customerId param from path url that means id of customer in api
     * @return list of accountsDto those belong only to customer with id from path
     */
    List<AccountDto> findAll(int customerId);

    /**
     *
     * @param accountId one of accounts that belongs to customer
     * @param customerId param from path for validation, compare it to id of customer from account
     * @return accountDto with id from path
     */
    AccountDto findById(int accountId, int customerId);

    /**
     *
     * @param customerId param from path for validation, compare it to id of customer from account
     * @return account. It allows know account id and account
     */
    AccountDto createNew(int customerId);
}
