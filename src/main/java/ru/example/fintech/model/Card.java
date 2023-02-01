package ru.example.fintech.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Random;

@Entity
@Table(name = "cards", indexes = @Index(columnList = "account_id"))
@Data
@AllArgsConstructor()
@NoArgsConstructor
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private Account account;
    private String numberCard;
    private boolean isActivated;

    @Version
    private int version;

    {
        String firstFourNumber = String.format("%04d",new Random().nextInt(9999));
        String secondFourNumber = String.format("%04d",new Random().nextInt(9999));
        String thirdFourNumber = String.format("%04d",new Random().nextInt(9999));
        String fourthFourNumber = String.format("%04d",new Random().nextInt(9999));
        String number = firstFourNumber + " "
                + secondFourNumber + " "
                + thirdFourNumber + " "
                + fourthFourNumber;
        this.numberCard = number;
    }

    public Card(Account account) {
        this.account = account;
        this.isActivated = false;
    }
}
