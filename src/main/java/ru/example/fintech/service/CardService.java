package ru.example.fintech.service;

import ru.example.fintech.dto.CardDto;
import ru.example.fintech.dto.DepositDto;

import java.util.List;

/**
 * Service contain logic for work and validation with operations by card
 */
public interface CardService {
    /**
     * It allows creating new card belongs to account
     * @param customerId param for validation
     * @param accountId created card will belong to this accounts
     * @return info about new car
     */
    CardDto createCard(int accountId, int customerId);

    /**
     * It allows seeing all cards belongs to this account
     * @param customerId param for validation
     * @param accountId par for validation
     * @return all cards those belong to account
     */
    List<CardDto> findAllCard(int customerId, int accountId);

    /**
     * It allows get info about one card, main reason usually is get info about balance
     * @param customerId param for validation
     * @param accountId param for validation
     * @param cardId id of card
     * @return info about card
     */
    CardDto checkBalance(int customerId, int accountId, int cardId);

    /**
     * Allows to make deposit on card from 10 rub to 1000_000 rub. Any deposit activate card
     * @param customerId param for validation
     * @param accountId param for validation
     * @param cardId id of card
     * @param depositDto contains sum that customer want to add on him account
     * @return updated card
     */
    CardDto makeDeposit(int customerId, int accountId, int cardId, DepositDto depositDto);
}
