package ru.example.fintech.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class DepositDto {

    private BigDecimal deposit;

    public DepositDto(BigDecimal deposit) {
        this.deposit = deposit;
    }

}
