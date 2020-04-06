package com.acmebank.accountmanager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

@RestController
public class AccountManager {

    @Autowired
    AccountRepository accountRepository;

    @GetMapping("/balance/{account}")
    public Optional<Account> getBalance(@PathVariable String account) {
        long accountId = Long.parseLong(account);
        return accountRepository.findById(accountId);
    }

    @PostMapping("/transfer")
    @ResponseBody
    public ResponseEntity transfer(@RequestBody Map<String, String> body) {

        try {
            long from = Long.parseLong(body.get("from"));
            long to = Long.parseLong(body.get("to"));
            BigDecimal amount = new BigDecimal(body.get("amount"));

            if (accountRepository.transfer(amount, from, to)) {
                return new ResponseEntity(HttpStatus.OK);
            } else {
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }
        } catch (NumberFormatException ex) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }
}
