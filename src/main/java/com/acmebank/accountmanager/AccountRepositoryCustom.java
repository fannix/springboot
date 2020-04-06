package com.acmebank.accountmanager;

import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

public interface AccountRepositoryCustom {

    @Transactional
    public boolean transfer(BigDecimal amount, long from, long to);
}
