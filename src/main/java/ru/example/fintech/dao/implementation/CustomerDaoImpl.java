package ru.example.fintech.dao.implementation;

import org.springframework.stereotype.Repository;
import ru.example.fintech.model.Customer;
import ru.example.fintech.dao.CustomerDao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

@Repository
public class CustomerDaoImpl implements CustomerDao {

    private final EntityManagerFactory entityManagerFactory;

    public CustomerDaoImpl(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public List<Customer> findAll(){

        EntityManager entityManager = getEntityManagerWithTransaction();
        List<Customer> customers = entityManager.createQuery("from Customer", Customer.class).getResultList();
        closeEntityManager(entityManager);
        return customers;
    }

    @Override
    public Customer findById(int id){
        EntityManager entityManager = getEntityManagerWithTransaction();
        Customer customer = entityManager.find(Customer.class, id);
        closeEntityManager(entityManager);
        return customer;
    }

    @Override
    public void create(Customer customer){
        EntityManager entityManager = getEntityManagerWithTransaction();
        entityManager.persist(customer);
        closeEntityManager(entityManager);
    }

    @Override
    public void update(int id, Customer updatedCustomer){
        EntityManager entityManager = getEntityManagerWithTransaction();
        Customer customer = entityManager.find(Customer.class, id);
        customer.setFirstName(updatedCustomer.getFirstName());
        customer.setLastName(updatedCustomer.getLastName());
        customer.setPassport(customer.getPassport());
        customer.setPhone(customer.getPhone());
        closeEntityManager(entityManager);
    }

    @Override
    public void delete(int id){
        EntityManager entityManager = getEntityManagerWithTransaction();
        Customer customer = entityManager.find(Customer.class, id);
        entityManager.remove(customer);
        closeEntityManager(entityManager);
    }

    private void closeEntityManager(EntityManager entityManager) {
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    private EntityManager getEntityManagerWithTransaction() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        return entityManager;
    }
}
