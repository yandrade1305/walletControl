package br.com.tracefinance.walletControl.domain.form;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class WalletForm {
    private Long idWallet;
    private String ownerName;
    private BigDecimal walletValue;

    public void setIdWallet(Long idWallet) {
        this.idWallet = idWallet;
    }
}
