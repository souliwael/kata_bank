package comkata.bankaccount.infrastructure.controlleradvice;

import comkata.bankaccount.application.exceptions.AccountNotFoundException;
import comkata.bankaccount.application.exceptions.InvalidAmountException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class BankControllerAdvice {

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<String> handleAccountNotFoundException(AccountNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidAmountException.class)
    public ResponseEntity<String> handleInsufficientFundsException(InvalidAmountException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_ACCEPTABLE);
    }
}
