package ru.example.fintech.service.implemetation;

import org.springframework.stereotype.Service;
import ru.example.fintech.dao.AccountDao;
import ru.example.fintech.dao.CardDao;
import ru.example.fintech.model.Account;
import ru.example.fintech.model.Card;
import ru.example.fintech.dto.CardDto;
import ru.example.fintech.dto.DepositDto;
import ru.example.fintech.exception.ApiRequestException;
import ru.example.fintech.service.CardService;

import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.List;

@Service
public class CardServiceImpl implements CardService {

    final CardDao cardDao;
    final AccountDao accountDao;
    boolean isSetUp;
    final EntityManagerFactory entityManagerFactory;


    public CardServiceImpl(CardDao cardDao, AccountDao accountDao, EntityManagerFactory entityManagerFactory) {
        this.cardDao = cardDao;
        this.accountDao = accountDao;
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public CardDto createCard(int accountId, int customerId) {
        Account account = accountDao.findById(accountId);

        if (account == null || (account.getCustomer().getId() != customerId)) {
            throw new ApiRequestException("Счет не принадлежит данному клиенту или вовсе не существует");
        }
        Card card = cardDao.create(accountId);

        return new CardDto(card.getAccount().getId(), card.getId(),
                card.getNumberCard(), card.getAccount().getBalance(), card.isActivated());
    }

    ;

    @Override
    public List<CardDto> findAllCard(int customerId, int accountId) {

        Account account = accountDao.findById(accountId);
        if (account == null || (account.getCustomer().getId() != customerId)){
            throw new ApiRequestException("Ошибка! Счет не существует или не принадлежат клиенту. Перепроверьте URL");
        }
        List<CardDto> cardsDto = new ArrayList<>();
        cardDao.findAllCard(accountId).forEach(card -> {
            cardsDto.add(new CardDto(card.getAccount().getId(), card.getId(),
                    card.getNumberCard(), card.getAccount().getBalance(), card.isActivated()));
        });
        return cardsDto;
    }

    @Override
    public CardDto checkBalance(int customerId, int accountId, int cardId) {

        Card card = validatePath(customerId, accountId, cardId);

        return new CardDto(card.getAccount().getId(), card.getId(),
                card.getNumberCard(), card.getAccount().getBalance(), card.isActivated());
    }

    @Override
    public CardDto makeDeposit(int customerId, int accountId, int cardId, DepositDto depositDto) {

        Card card = validatePath(customerId, accountId, cardId);

        if (depositDto.getDeposit().doubleValue() <= 10 || depositDto.getDeposit().doubleValue() > 1_000_000) {
            throw new ApiRequestException("Недопустимая сумма. " +
                    "Сумма пополнения должна быть от 10 рублей и не более 1 миллиона рублей");
        }
        accountDao.updateAccount(accountId, depositDto);
        card.setActivated(true);
        card = cardDao.updateById(cardId, card);

        return new CardDto(card.getAccount().getId(), card.getId(),
                card.getNumberCard(), card.getAccount().getBalance(), card.isActivated());
    }


    private Card validatePath(int customerId, int accountId, int cardId) {
        Account account = accountDao.findById(accountId);
        Card card = cardDao.findById(cardId);
        if (card == null && account == null) {
            throw new ApiRequestException("Ошибка! Карта или счет не существуют. Перепроверьте URL");
        }
        if ((account.getCustomer().getId() != customerId)
                || (card.getAccount().getId() != accountId)) {
            throw new ApiRequestException("Ошибка! Карта или счет не принадлежат клиенту. Перепроверьте URL");
        }
        return card;
    }
}
