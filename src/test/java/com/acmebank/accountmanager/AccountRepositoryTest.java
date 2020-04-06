package com.acmebank.accountmanager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@DataJpaTest
public class AccountRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AccountRepository accountRepository;

    @Test
    public void testAddAccount() {
        Account newAccount = new Account("HKD", BigDecimal.valueOf(100), "user3");
        long newId = accountRepository.save(newAccount).getId();

        Account savedAccount = accountRepository.findById(newId).get();
        assertEquals(savedAccount.getAmount(), newAccount.getAmount());
        assertEquals(savedAccount.getCurrencyCode(), newAccount.getCurrencyCode());
        assertEquals(savedAccount.getOwner(), newAccount.getOwner());
    }

    @Test
    public void testTransfer() {
        Account fromAccount = accountRepository.findById(1L).get();
        Account toAccount = accountRepository.findById(2L).get();

        assertEquals(BigDecimal.valueOf(100), fromAccount.getAmount());
        assertEquals(BigDecimal.valueOf(200), toAccount.getAmount());

        accountRepository.transfer(BigDecimal.valueOf(10), fromAccount.getId(), toAccount.getId());

        assertEquals(BigDecimal.valueOf(90), fromAccount.getAmount());
        assertEquals(BigDecimal.valueOf(210), toAccount.getAmount());
    }

    @Test
    public void testInvalidTransfer() {
        assertFalse(accountRepository.transfer(BigDecimal.valueOf(10), 1L, 3L));
        assertFalse(accountRepository.transfer(BigDecimal.valueOf(100000), 1L, 2L));
    }
}
