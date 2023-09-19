package comkata.bankaccount.infrastructure.controller;

import comkata.bankaccount.domain.enums.OperationType;
import comkata.bankaccount.utils.TestDataBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BankControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void depositShouldAddAmountToBalance() throws Exception {

        mockMvc.perform(post("/bank-accounts/deposit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestDataBuilder.buildRequestBody(1L, BigDecimal.valueOf(100))))
                .andExpect(status().isAccepted());
    }

    @Test
    public void withdrawalShouldSubtractAmountFromBalance() throws Exception {
        mockMvc.perform(post("/bank-accounts/withdraw")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestDataBuilder.buildRequestBody(2L, BigDecimal.valueOf(200))))
                .andExpect(status().isAccepted());
    }

    @Test
    public void statementShouldReturnHistoryOfOperations() throws Exception {
        mockMvc.perform(get("/bank-accounts/statement/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].amount", equalTo(100.0)))
                .andExpect(jsonPath("$[0].balance", equalTo(0.0)))
                .andExpect(jsonPath("$[0].operationType", equalTo(OperationType.DEPOSIT.toString())))
                .andExpect(jsonPath("$[1].amount", equalTo(50.0)))
                .andExpect(jsonPath("$[1].balance", equalTo(100.0)))
                .andExpect(jsonPath("$[1].operationType", equalTo(OperationType.WITHDRAW.toString())));
    }

    @Test
    public void balanceShouldReturnAccountBalance() throws Exception {
        mockMvc.perform(get("/bank-accounts/balance/1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("50.00"));
    }
}
