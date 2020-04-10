package com.acmebank.accountmanager;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = AccountManager.class)
public class AccountManagerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private AccountRepository accountRepository;

    @Test
    public void testAccountBalance() throws Exception {
        Account testAccount = new Account("HKD", BigDecimal.valueOf(1L), "test");
        when(accountRepository.findById(1L)).thenReturn(Optional.of(testAccount));
        when(accountRepository.findById(2L)).thenReturn(Optional.empty());

        mvc.perform(get("/balance/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.owner", is(testAccount.getOwner())));

        mvc.perform(get("/balance/2").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    public void testTransferBalance() throws Exception {
        when(accountRepository.transfer(BigDecimal.valueOf(100L), 1L, 2L)).thenReturn(true);
        when(accountRepository.transfer(BigDecimal.valueOf(100L), 1L, 3L)).thenReturn(false);

        mvc.perform(
                post("/transfer")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("from", "1")
                        .param("to", "2")
                        .param("amount", "100")
        ).andExpect(status().isOk())
                .andExpect(content().string("Transaction complete"));

        mvc.perform(
                post("/transfer")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("from", "1")
                        .param("to", "3")
                        .param("amount", "100")
        ).andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid request"));
    }
}
