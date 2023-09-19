package comkata.bankaccount.application.service;

import comkata.bankaccount.application.exceptions.AccountNotFoundException;
import comkata.bankaccount.application.exceptions.InvalidAmountException;
import comkata.bankaccount.infrastructure.dto.TransactionRequest;
import comkata.bankaccount.domain.entity.Account;
import comkata.bankaccount.domain.enums.OperationType;
import comkata.bankaccount.domain.entity.Transaction;
import comkata.bankaccount.infrastructure.repository.AccountRepository;
import comkata.bankaccount.infrastructure.repository.TransactionRepository;
import comkata.bankaccount.application.service.impl.AccountServiceImpl;
import comkata.bankaccount.utils.TestDataBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private AccountServiceImpl accountService;

    @Test
    public void testDeposit() throws InvalidAmountException, AccountNotFoundException {
        // Arrange
        TransactionRequest transactionRequest =
                TestDataBuilder.buildTransactionRequest(1L, BigDecimal.valueOf(100));
        when(transactionRepository.save(any(Transaction.class))).thenReturn(new Transaction());
        when(accountRepository.findById(anyLong()))
                .thenReturn(Optional.of(new Account(1L, new ArrayList<>(), BigDecimal.valueOf(100))));

        // Act
        accountService.deposit(transactionRequest);

        // Assert
        verify(transactionRepository, times(1)).save(any(Transaction.class));
        verify(accountRepository, times(1)).save(any(Account.class));
    }

    @Test
    public void shouldWithdrawWhenAccountBalanceGreaterOrEqualAmountRequested() throws InvalidAmountException, AccountNotFoundException {
        // Arrange
        TransactionRequest transactionRequest =
                TestDataBuilder.buildTransactionRequest(1L, BigDecimal.valueOf(100));
        when(transactionRepository.save(any(Transaction.class))).thenReturn(new Transaction());
        when(accountRepository.findById(anyLong()))
                .thenReturn(Optional.of(new Account(1L, new ArrayList<>(), BigDecimal.valueOf(100))));

        // Act
        accountService.withdraw(transactionRequest);

        // Assert
        verify(transactionRepository, times(1)).save(any(Transaction.class));
        verify(accountRepository, times(1)).save(any(Account.class));
    }

    @Test
    public void shouldThrowInvalidAmountExceptionWhenWithdrawalAndInsufficientBalance() {
        // Arrange
        TransactionRequest transactionRequest =
                TestDataBuilder.buildTransactionRequest(1L, BigDecimal.valueOf(500));
        when(accountRepository.findById(anyLong()))
                .thenReturn(Optional.of(new Account(1L, new ArrayList<>(), BigDecimal.valueOf(100))));

        // Act And Assert
        assertThatThrownBy(() -> accountService.withdraw(transactionRequest))
                .isInstanceOf(InvalidAmountException.class);
    }


    @Test
    public void testGetStatement() throws AccountNotFoundException {
        // Arrange
        List<Transaction> transactions =
                Arrays.asList(new Transaction(LocalDateTime.now(), BigDecimal.valueOf(100), BigDecimal.valueOf(100), OperationType.DEPOSIT,null));
        when(accountRepository.findById(anyLong()))
                .thenReturn(Optional.of(new Account(1L, transactions, BigDecimal.valueOf(100))));

        // Act
        List<Transaction> result = accountService.getStatements(1L);

        // Assert
        assertEquals("Check first transaction", transactions.get(0), result.get(0));
    }
}