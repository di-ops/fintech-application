package ru.example.fintech.dao.implementation;

import org.springframework.stereotype.Repository;
import ru.example.fintech.model.Account;
import ru.example.fintech.dao.CardDao;
import ru.example.fintech.model.Card;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

@Repository
public class CardDaoImpl implements CardDao {

    private final EntityManagerFactory entityManagerFactory;

    public CardDaoImpl(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public List<Card> findAllCard(int accountId) {

        EntityManager entityManager = getEntityManagerWithTransaction();
        List<Card> cards = entityManager
                .createQuery("from Card c where c.account.id = ?1", Card.class)
                .setParameter(1, accountId).getResultList();
        closeEntityManager(entityManager);
        return cards;
    }


    @Override
    public Card create(int accountId) {
        EntityManager entityManager = getEntityManagerWithTransaction();
        Account account = entityManager.find(Account.class, accountId);
        Card card = new Card(account);
        entityManager.persist(card);
        closeEntityManager(entityManager);
        return card;
    }

    @Override
    public Card findById(int cardId) {
        EntityManager entityManager = getEntityManagerWithTransaction();
        Card card = entityManager.find(Card.class, cardId);
        closeEntityManager(entityManager);
        return card;
    }

    @Override
    public Card updateById(int cardId, Card cardWithUpdatedStatus) {
        EntityManager entityManager = getEntityManagerWithTransaction();
        Card updatedCard = entityManager.find(Card.class, cardId);
        updatedCard.setActivated(cardWithUpdatedStatus.isActivated());
        closeEntityManager(entityManager);
        return updatedCard;
    }

    private EntityManager getEntityManagerWithTransaction() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        return entityManager;
    }

    private void closeEntityManager(EntityManager entityManager) {
        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
