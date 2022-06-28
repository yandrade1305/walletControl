package br.com.tracefinance.walletControl.domain.dto;

import br.com.tracefinance.walletControl.domain.entity.Wallet;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@Getter
public class WalletLimitDTO {
    private BigDecimal value;

    public WalletLimitDTO(BigDecimal value){
        this.value = value;
    }
}
