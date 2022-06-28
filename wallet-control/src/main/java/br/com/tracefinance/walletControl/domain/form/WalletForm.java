package br.com.tracefinance.walletControl.domain.form;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class WalletForm {
    private Long idWallet;
    private String ownerName;

    public void setIdWallet(Long idWallet) {
        this.idWallet = idWallet;
    }
}
