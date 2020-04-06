package com.acmebank.accountmanager;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.InvalidTransactionException;
import java.math.BigDecimal;

@Repository
public class AccountRepositoryImpl implements AccountRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

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
