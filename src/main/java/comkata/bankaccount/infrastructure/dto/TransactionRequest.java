package comkata.bankaccount.infrastructure.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class TransactionRequest {
    Long accountId;

    BigDecimal amount;
}
