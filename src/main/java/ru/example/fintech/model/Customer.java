package ru.example.fintech.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "customers", uniqueConstraints =
        {
                @UniqueConstraint(columnNames = "phone"),
                @UniqueConstraint(columnNames = "passport")
        })
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    private String phone;
    private String passport;

    @Version
    private int version;

    public Customer(String firstName, String lastName, String phone, String passport) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.passport = passport;
    }
}
