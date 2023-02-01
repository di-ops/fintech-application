package ru.example.fintech.dao.implementation;

import org.springframework.stereotype.Repository;
import ru.example.fintech.model.Account;
import ru.example.fintech.model.Customer;
import ru.example.fintech.dao.AccountDao;
import ru.example.fintech.dto.DepositDto;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.math.BigDecimal;
import java.util.List;

@Repository
public class AccountDaoImpl implements AccountDao {

    private final EntityManagerFactory entityManagerFactory;

    public AccountDaoImpl(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public List<Account> findAll(int customerId){
        EntityManager entityManager = getEntityManagerWithTransaction();
        List<Account> accounts = entityManager
                .createQuery("from Account a where a.customer.id = ?1", Account.class)
                .setParameter(1, customerId).getResultList();
        closeEntityManager(entityManager);
        return accounts;
   }


    @Override
    public Account findById(int accountId) {
        EntityManager entityManager = getEntityManagerWithTransaction();
        Account account = entityManager.find(Account.class, accountId);
        closeEntityManager(entityManager);
        return account;
    }

    @Override
    public Account create(int customerId) {
        EntityManager entityManager = getEntityManagerWithTransaction();
        Customer customer = entityManager.find(Customer.class, customerId);
        Account account = new Account(customer);
        entityManager.persist(account);
        closeEntityManager(entityManager);
        return account;
    }

    @Override
    public Account updateAccount(int accountId, DepositDto depositDto) {
        EntityManager entityManager = getEntityManagerWithTransaction();
        Account accountUpdated = entityManager.find(Account.class, accountId);
        BigDecimal balance = accountUpdated.getBalance().add(depositDto.getDeposit());
        accountUpdated.setBalance(balance);
        closeEntityManager(entityManager);
        return accountUpdated;
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
