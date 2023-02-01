package ru.example.fintech.service;

import ru.example.fintech.dto.CustomerDto;

import java.util.List;


public interface CustomerService {

    List<CustomerDto> findAll();


    CustomerDto findById(int id);


    void create(CustomerDto customerDto);


    void update(int id, CustomerDto customerDto);

    void delete(int id);
}
