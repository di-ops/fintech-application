package ru.example.fintech.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {

    private int accountId;
    private BigDecimal balance;
    private int customerId;
    private List<Integer> cardsId;
}
