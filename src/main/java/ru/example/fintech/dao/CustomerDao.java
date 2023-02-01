package ru.example.fintech.dao;

import ru.example.fintech.model.Customer;

import java.util.List;

/**
 *
 */
public interface CustomerDao {
    /**
     *
     * @return
     */
    List<Customer> findAll();

    /**
     *
     * @param id
     * @return
     */
    Customer findById(int id);

    /**
     *
     * @param customer
     */
    void create(Customer customer);

    /**
     *
     * @param id
     * @param updatedCustomer
     */
    void update(int id, Customer updatedCustomer);

    /**
     *
     * @param id
     */
    void delete(int id);
}
