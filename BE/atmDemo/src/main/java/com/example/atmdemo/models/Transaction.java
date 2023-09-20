package com.example.atmdemo.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Transaction {
    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "transaction_type")
    private String transactionType;
    @Column(name = "amount")
    private BigDecimal amount;
    @Column(name = "date")
    private LocalDateTime date;
    @ManyToOne
    @JoinColumn(name = "user_account_id")
    private UserAccount userAccount;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
