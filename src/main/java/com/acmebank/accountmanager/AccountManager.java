package com.acmebank.accountmanager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

/**
 * Controller of this app
 * <p>
 * handle get balance request and transfer request
 */
@RestController
public class AccountManager {

    Logger logger = LoggerFactory.getLogger(AccountManager.class);

    @Autowired
    AccountRepository accountRepository;

    @GetMapping("/balance/{account}")
    public Optional<Account> getBalance(@PathVariable String account) {
        long accountId = Long.parseLong(account);
        return accountRepository.findById(accountId);
    }

    @PostMapping(value = "/transfer")
    @ResponseBody
    public ResponseEntity<String> transfer(@RequestParam Map<String, String> params) {
        logger.info(params.toString());

        try {
            long from = Long.parseLong(params.get("from"));
            long to = Long.parseLong(params.get("to"));
            BigDecimal amount = new BigDecimal(params.get("amount"));

            if (accountRepository.transfer(amount, from, to)) {
                return new ResponseEntity<>("Transaction complete", HttpStatus.OK);
            } else {
                logger.warn("Received an invalid request");
                return new ResponseEntity<>("Invalid request", HttpStatus.BAD_REQUEST);
            }
        } catch (NumberFormatException ex) {
            logger.warn("Invalid account format exception {0}", ex);
            return new ResponseEntity<>("Invalid account or amount", HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            logger.error("Exception {0} was thrown during transaction execution", ex);
            return new ResponseEntity<>("Transaction failed", HttpStatus.BAD_REQUEST);
        }
    }
}
