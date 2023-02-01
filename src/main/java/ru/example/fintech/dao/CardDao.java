package ru.example.fintech.dao;

import ru.example.fintech.model.Card;

import java.util.List;

/**
 * layer for work with table cards from our db
 */
public interface CardDao {
    /**
     *
     * @param accountId param allows get cards those belong one account
     * @return list of cards
     */
    List<Card> findAllCard(int accountId);

    /**
     *
     * @param accountId unique id of account
     * @return new card that will belong account with this id
     */
    Card create(int accountId);

    /**
     *
     * @param cardId unique id of card
     * @return card with id from args
     */
    Card findById(int cardId);

    /**
     *
     * @param cardId unique id of card
     * @param cardWithUpdatedStatus status of card. Can be activated and not
     * @return updated card
     */
    Card updateById(int cardId, Card cardWithUpdatedStatus);
}
