package comkata.bankaccount.infrastructure.controller;

import comkata.bankaccount.application.exceptions.AccountNotFoundException;
import comkata.bankaccount.application.exceptions.InvalidAmountException;
import comkata.bankaccount.infrastructure.dto.TransactionRequest;
import comkata.bankaccount.domain.entity.Account;
import comkata.bankaccount.domain.entity.Transaction;
import comkata.bankaccount.application.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/bank-accounts")
public class BankController {

    private AccountService accountService;

    @Autowired
    public BankController(AccountService accountService) {
        this.accountService = accountService;
    }

    /**
     * @param transactionRequest
     * @return
     * @throws InvalidAmountException
     * @throws AccountNotFoundException
     */
    @PostMapping("/deposit")
    public ResponseEntity<Account> deposit(@RequestBody TransactionRequest transactionRequest) throws InvalidAmountException, AccountNotFoundException {
        return new ResponseEntity<>(accountService.deposit(transactionRequest), HttpStatus.ACCEPTED);
    }

    /**
     * @param transactionRequest
     * @return
     * @throws InvalidAmountException
     * @throws AccountNotFoundException
     */
    @PostMapping("/withdraw")
    public ResponseEntity<Account> withdraw(@RequestBody TransactionRequest transactionRequest) throws InvalidAmountException, AccountNotFoundException {
        return new ResponseEntity<>(accountService.withdraw(transactionRequest), HttpStatus.ACCEPTED);
    }

    /**
     * @param id
     * @return
     * @throws AccountNotFoundException
     */
    @GetMapping("/statement/{id}")
    public ResponseEntity<List<Transaction>> statements(@PathVariable Long id) throws AccountNotFoundException {
        return new ResponseEntity<>(accountService.getStatements(id), HttpStatus.OK);
    }

    /**
     * @param id
     * @return
     * @throws AccountNotFoundException
     */
    @GetMapping("/balance/{id}")
    public ResponseEntity<BigDecimal> accountBalance(@PathVariable Long id) throws AccountNotFoundException {
        return new ResponseEntity<>(accountService.getBalance(id), HttpStatus.OK);
    }
}
