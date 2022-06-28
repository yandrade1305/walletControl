package br.com.tracefinance.walletControl.domain.entity;

import br.com.tracefinance.walletControl.domain.form.WalletForm;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

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

    public Wallet(WalletForm walletForm) {
        this.idWallet = walletForm.getIdWallet();
        this.ownerName = walletForm.getOwnerName();
        this.walletValue = walletForm.getWalletValue();
    }
}
