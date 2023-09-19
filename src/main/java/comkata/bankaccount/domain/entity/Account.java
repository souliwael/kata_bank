package comkata.bankaccount.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "account",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Transaction> transactions;

    @Column(name = "balance", precision = 10, scale = 2)
    private BigDecimal balance;
}
