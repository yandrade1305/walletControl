package br.com.tracefinance.walletControl.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "PAYMENT", schema = "WALLET_CONTROL")
@NoArgsConstructor
@Getter
@Setter
public class Payment {
    @Id
    @SequenceGenerator(name = "WALLET_CONTROL.ID_PAYMENT_SEQ", sequenceName = "WALLET_CONTROL.ID_PAYMENT_SEQ", initialValue = 1, allocationSize = 1)
    @GeneratedValue(generator = "WALLET_CONTROL.ID_PAYMENT_SEQ", strategy = GenerationType.SEQUENCE)
    @Column (name = "ID_PAYMENT")
    private Long idPayment;
    @Column (name = "AMOUNT")
    private BigDecimal amount;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_WALLET", insertable = false, updatable = false)
    private Wallet wallet;
    @Column(name = "ID_WALLET")
    private Long idWallet;
    @Column(name = "PAYMENT_DATE")
    private LocalDate paymentDate;
}
