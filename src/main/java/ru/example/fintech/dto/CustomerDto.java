package ru.example.fintech.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
public class CustomerDto {

    private int id;
    private String firstName;
    private String lastName;
    private String phone;
    private String passport;

    private List<Integer> accountsId;

    public CustomerDto(String firstName, String lastName, String phone, String passport) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.passport = passport;
    }

    public CustomerDto(int id, String firstName, String lastName, String phone, String passport) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.passport = passport;
    }
}
