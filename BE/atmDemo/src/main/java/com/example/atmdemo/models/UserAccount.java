package com.example.atmdemo.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserAccount {
    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "account_number")
    private int accountNumber;
    @Column(name = "balance")
    private BigDecimal balance;
    @Column(name = "account_type")
    private String accountType;
    @OneToMany(mappedBy = "userAccount")
    private List<Transaction> transactions;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
