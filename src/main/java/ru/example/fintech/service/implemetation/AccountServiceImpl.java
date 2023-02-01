package ru.example.fintech.service.implemetation;

import org.springframework.stereotype.Service;
import ru.example.fintech.dao.AccountDao;
import ru.example.fintech.dao.CardDao;
import ru.example.fintech.dao.CustomerDao;
import ru.example.fintech.model.Account;
import ru.example.fintech.dto.AccountDto;
import ru.example.fintech.exception.ApiRequestException;
import ru.example.fintech.service.AccountService;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountDao accountDao;
    private final CardDao cardDao;
    private final CustomerDao customerDao;

    public AccountServiceImpl(AccountDao accountDao, CardDao cardDao, CustomerDao customerDao) {
        this.accountDao = accountDao;
        this.cardDao = cardDao;
        this.customerDao = customerDao;
    }

    @Override
    public List<AccountDto> findAll(int customerId) {
       if (customerDao.findById(customerId) == null){
           throw new ApiRequestException("Пользователь не существует");
       }

        List<AccountDto> accountsDto = new ArrayList<>();
            List<Account> accounts = accountDao.findAll(customerId);
            accounts.forEach(account -> {
                List<Integer> cardsId = new ArrayList<>();
                cardDao.findAllCard(account.getId()).forEach(card -> cardsId.add(card.getId()));
                accountsDto.add(new AccountDto(account.getId(), account.getBalance(), account.getCustomer().getId(), cardsId));
            });
        return accountsDto;
    }

    @Override
    public AccountDto findById(int accountId, int customerId) {
        Account byId = accountDao.findById(accountId);
        if (byId == null || (byId.getCustomer().getId() != customerId)) {
            throw new ApiRequestException("Вы можете получить доступ только к своему счету");
        }
        List<Integer> cardsId = new ArrayList<>();
        cardDao.findAllCard(accountId).forEach(card -> cardsId.add(card.getId()));
        return new AccountDto(byId.getId(), byId.getBalance(), byId.getCustomer().getId(), cardsId);
    }

    @Override
    public AccountDto createNew(int customerId) {
        int size = accountDao.findAll(customerId).size();
        if (size > 5) {
            throw new ApiRequestException("Клиент не может иметь больше 5 счетов");
        }
        try {
            Account account = accountDao.create(customerId);
            List<Integer> cardsId = new ArrayList<>();
            return new AccountDto(account.getId(), account.getBalance(), account.getCustomer().getId(), cardsId);
        }
         catch (RuntimeException e){
            throw new ApiRequestException("Вы пытаетсесь создать счет для несуществующего пользователя");
        }
    }
}
