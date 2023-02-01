package ru.example.fintech.service.implemetation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.example.fintech.dao.AccountDao;
import ru.example.fintech.dao.CustomerDao;
import ru.example.fintech.model.Customer;
import ru.example.fintech.dto.CustomerDto;
import ru.example.fintech.exception.ApiRequestException;
import ru.example.fintech.service.CustomerService;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerDao customerDao;
    private final AccountDao accountDao;

    public CustomerServiceImpl(CustomerDao customerDao, AccountDao accountDao) {
        this.customerDao = customerDao;
        this.accountDao = accountDao;
    }

    @Override
    public List<CustomerDto> findAll() {
        List<Customer> customers = customerDao.findAll();
        List<CustomerDto> customersDto = new ArrayList<>();

        for (Customer c : customers) {
            CustomerDto customerDto = CustomerToCustomerDto(c);
            List<Integer> accountsId = new ArrayList<>();
            accountDao.findAll(c.getId()).forEach(account -> accountsId.add(account.getId()));
            customerDto.setAccountsId(accountsId);
            customersDto.add(customerDto);
        }

        return customersDto;
    }


    @Override
    public CustomerDto findById(int id) {
        try {
            Customer customer = customerDao.findById(id);
            CustomerDto customerDto = CustomerToCustomerDto(customer);
            List<Integer> accountsId = new ArrayList<>();
            accountDao.findAll(customer.getId()).forEach(account -> accountsId.add(account.getId()));
            customerDto.setAccountsId(accountsId);
            return customerDto;

        } catch (RuntimeException e) {
            throw new ApiRequestException("Пользователь не существует");
        }
    }

    @Override
    public void create(CustomerDto customerDto) {
        Customer customer = new Customer(customerDto.getFirstName(), customerDto.getLastName(),
                customerDto.getPhone(), customerDto.getPassport());
        //проверка уникальности телефона и паспорта
        try {
            customerDao.create(customer);
        } catch (RuntimeException e) {
            throw new ApiRequestException("Ошибка создания клиента. Возможно вы есть в базе или телефон уже используется. " +
                    "При необходимости обратись по горячей линии или в одно из отделений банка");
        }
    }

    @Override
    public void update(int id, CustomerDto customerDto) {
        Customer customer = new Customer(customerDto.getFirstName(), customerDto.getLastName(),
                customerDto.getPhone(), customerDto.getPassport());
        try {
            customerDao.update(id, customer);
        } catch (RuntimeException e){
            throw new ApiRequestException("Проверьте корректность данных. Поля не могут быть пустыми, а паспорт и телефон должны быть уникальными");
        }

    }

    @Override
    public void delete(int id) {
        customerDao.delete(id);
    }

    private CustomerDto CustomerToCustomerDto(Customer c) {
        return new CustomerDto(c.getId(), c.getFirstName(), c.getLastName(),
                c.getPhone(), c.getPassport());
    }
}
