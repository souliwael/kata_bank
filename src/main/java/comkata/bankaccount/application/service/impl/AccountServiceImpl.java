package comkata.bankaccount.application.service.impl;

import comkata.bankaccount.application.exceptions.AccountNotFoundException;
import comkata.bankaccount.application.exceptions.InvalidAmountException;
import comkata.bankaccount.infrastructure.dto.TransactionRequest;
import comkata.bankaccount.domain.entity.Account;
import comkata.bankaccount.domain.enums.OperationType;
import comkata.bankaccount.domain.entity.Transaction;
import comkata.bankaccount.infrastructure.repository.AccountRepository;
import comkata.bankaccount.infrastructure.repository.TransactionRepository;
import comkata.bankaccount.application.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;
    private TransactionRepository transactionRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository,
                              TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Account deposit(TransactionRequest transactionRequest) throws InvalidAmountException, AccountNotFoundException {
        BigDecimal amount = transactionRequest.getAmount();
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            log.warn("Amount must be greater than 0");
            throw new InvalidAmountException("Amount must be greater than 0");
        }
        Account account = accountCheck(transactionRequest.getAccountId());
        account.setBalance(account.getBalance().add(amount));
        Transaction transaction = saveTransaction(amount, account, OperationType.DEPOSIT);
        account.getTransactions().add(transaction);
        return accountRepository.save(account);
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Account withdraw(TransactionRequest transactionRequest) throws InvalidAmountException, AccountNotFoundException {
        Account account = accountCheck(transactionRequest.getAccountId());
        BigDecimal amount = transactionRequest.getAmount();
        if (amount.compareTo(account.getBalance()) > 0) {
            log.warn("Insufficient Balance.");
            throw new InvalidAmountException("Amount requested is greater than the balance");
        }
        account.setBalance(account.getBalance().subtract(amount));
        Transaction transaction = saveTransaction(amount, account, OperationType.WITHDRAW);
        account.getTransactions().add(transaction);
        return accountRepository.save(account);
    }

    @Override
    public List<Transaction> getStatements(Long id) throws AccountNotFoundException {
        Account account = accountCheck(id);
        account.getTransactions().stream()
                .forEach(transaction -> log.info("Transaction Date {}, Transaction Amount {}, Current Balance {}",
                        transaction.getTransactionDate(), transaction.getAmount(), transaction.getBalance()));
        return account.getTransactions();
    }

    @Override
    public BigDecimal getBalance(Long id) throws AccountNotFoundException {
        Account account = accountCheck(id);
        return account.getBalance();
    }

    private Account accountCheck(Long id) throws AccountNotFoundException {
        log.info("Fetching account information ...");
        return accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException("Account was not found"));
    }


    private Transaction saveTransaction(BigDecimal amount, Account account, OperationType operationType) {
        log.info("Saving transaction ...");
        Transaction transaction = new Transaction(LocalDateTime.now(), amount, account.getBalance(), operationType,account);
        transactionRepository.save(transaction);
        return transaction;
    }
}
