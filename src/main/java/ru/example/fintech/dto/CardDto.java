package ru.example.fintech.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class CardDto {


    private int accountId;
    private int cardId;
    private String numberOfCard;
    private BigDecimal balance;
    private boolean isActivated;

    public CardDto(int accountId, int cardId, String numberOfCard, BigDecimal balance, boolean isActivated) {
        this.accountId = accountId;
        this.cardId = cardId;
        this.numberOfCard = numberOfCard;
        this.balance = balance;
        this.isActivated = isActivated;
    }


}
