package com.acmebank.accountmanager;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;

/**
 * Implements the transfer functionality
 */
@Repository
public class AccountRepositoryImpl implements AccountRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    /**
     * Execute the transfer of money from one account to another account
     *
     * @param amount amount to transfer
     * @param fromId account to transfer money out of
     * @param toId   account to transfer money into
     * @return true if transfer complete, return false otherwise.
     */
    @Transactional
    @Override
    public boolean transfer(BigDecimal amount, long fromId, long toId) {
        if (fromId == toId) {
            return true;
        }

        Account from = entityManager.find(Account.class, fromId);
        Account to = entityManager.find(Account.class, toId);

        if (from == null || to == null) {
            return false;
        }

        if (from.getAmount().compareTo(amount) < 0) {
            return false;
        }

        from.setAmount(from.getAmount().subtract(amount));
        to.setAmount(to.getAmount().add(amount));

        return true;
    }
}
