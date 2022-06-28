package br.com.tracefinance.walletControl.domain.dto;

import br.com.tracefinance.walletControl.domain.entity.Wallet;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@Getter
public class WalletDTO {
    private Long idWallet;
    private String ownerName;

    public WalletDTO(Wallet wallet){
        this.idWallet = wallet.getIdWallet();
        this.ownerName = wallet.getOwnerName();
    }
}
