package ru.example.fintech.dao;

import ru.example.fintech.model.Account;
import ru.example.fintech.dto.DepositDto;

import java.util.List;

/**
 * layer for work with table account from our db
 */
public interface AccountDao {
    /**
     *
     * @param customerId unique id of customer
     * @return list of accounts those belong to this customer
     */
    List<Account> findAll(int customerId);

    /**
     *
     * @param accountId unique id of account
     * @return account with id from args
     */
    Account findById(int accountId);

    /**
     * Open new account
     * @param customerId unique id of customer
     * @return new account
     */
    Account create(int customerId);

    /**
     * method for update balance on account. It's used to make a deposit or make a transaction to counterparty
     * @param accountId unique id of customer
     * @param depositDto dto with sum
     * @return updated account
     */
    Account updateAccount(int accountId, DepositDto depositDto);
}
