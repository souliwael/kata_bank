package comkata.bankaccount.application.service;

import comkata.bankaccount.application.exceptions.AccountNotFoundException;
import comkata.bankaccount.application.exceptions.InvalidAmountException;
import comkata.bankaccount.infrastructure.dto.TransactionRequest;
import comkata.bankaccount.domain.entity.Account;
import comkata.bankaccount.domain.entity.Transaction;

import java.math.BigDecimal;
import java.util.List;

public interface AccountService {
    Account deposit(TransactionRequest transaction) throws InvalidAmountException, AccountNotFoundException;

    Account withdraw(TransactionRequest transaction) throws InvalidAmountException, AccountNotFoundException;

    List<Transaction> getStatements(Long id) throws AccountNotFoundException;

    BigDecimal getBalance(Long id) throws AccountNotFoundException;
}
