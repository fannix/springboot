package com.acmebank.accountmanager;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;

/**
 * Bank account holds the information about the amount of money, the currency code and the name of owner
 */
@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String currencyCode;
    private BigDecimal amount;

    private String owner;

    public Account(String currencyCode, BigDecimal amount, String owner) {
        this.currencyCode = currencyCode;
        this.amount = amount;
        this.owner = owner;
    }

    public Account() {
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getOwner() {
        return owner;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", currencyCode='" + currencyCode + '\'' +
                ", amount=" + amount +
                ", owner='" + owner + '\'' +
                '}';
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }
}
