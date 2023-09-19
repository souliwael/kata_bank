package comkata.bankaccount.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import comkata.bankaccount.domain.enums.OperationType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "transaction_date")
    private LocalDateTime transactionDate;

    @Column(name = "amount", precision = 10, scale = 2)
    private BigDecimal amount;

    @Column(name = "balance", precision = 10, scale = 2)
    private BigDecimal balance;

    @Enumerated(EnumType.STRING)
    @Column(name = "operation_type")
    private OperationType operationType;

    @ManyToOne
    @JoinColumn(name = "account_id")
    @JsonIgnore
    private Account account;

    public Transaction(LocalDateTime transactionDate, BigDecimal amount, BigDecimal balance, OperationType operationType, Account account) {
        this.transactionDate = transactionDate;
        this.amount = amount;
        this.balance = balance;
        this.operationType = operationType;
        this.account = account;
    }
}

