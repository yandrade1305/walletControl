package br.com.tracefinance.walletControl.domain.entity;

import br.com.tracefinance.walletControl.domain.form.WalletForm;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "WALLET", schema = "WALLET_CONTROL")
@NoArgsConstructor
@Getter
@Setter
public class Wallet {
    @Id
    @SequenceGenerator(name = "WALLET_CONTROL.ID_WALLET_SEQ", sequenceName = "WALLET_CONTROL.ID_WALLET_SEQ", initialValue = 1, allocationSize = 1)
    @GeneratedValue(generator = "WALLET_CONTROL.ID_WALLET_SEQ", strategy = GenerationType.SEQUENCE)
    @Column(name = "ID_WALLET")
    private Long idWallet;
    @Column(name = "OWNER_NAME")
    private String ownerName;
    @Column(name = "WALLET_VALUE")
    private BigDecimal walletValue;
    @Column(name = "LAST_PAYMENT")
    private LocalDateTime lastPayment;
    @Column(name = "DAYTIME_PAYMENT")
    private BigDecimal dayTimePayment;
    @Column(name = "NOCTURNAL_PAYMENT")
    private BigDecimal nocturnalPayment;
    @Column(name = "WEEKEND_PAYMENT")
    private BigDecimal weekendPayment;

    public Wallet(WalletForm walletForm) {
        this.idWallet = walletForm.getIdWallet();
        this.ownerName = walletForm.getOwnerName();
        this.walletValue = new BigDecimal("5000.00");
        this.lastPayment = LocalDateTime.now();
        this.dayTimePayment = BigDecimal.ZERO;
        this.nocturnalPayment = BigDecimal.ZERO;
        this.weekendPayment = BigDecimal.ZERO;
    }
}
