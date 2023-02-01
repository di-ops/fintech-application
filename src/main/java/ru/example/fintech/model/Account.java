package ru.example.fintech.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "accounts", indexes = @Index(columnList = "customer_id"))
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private BigDecimal balance;

    @ManyToOne
    private Customer customer;

    @Version
    private int version;

    public Account(Customer customer) {
        this.customer = customer;
        this.setBalance(new BigDecimal(0));
    }


}
