package br.com.tracefinance.walletControl.service;

import br.com.tracefinance.walletControl.domain.dto.WalletDTO;
import br.com.tracefinance.walletControl.domain.entity.Wallet;
import br.com.tracefinance.walletControl.domain.form.WalletForm;
import br.com.tracefinance.walletControl.domain.repository.WalletRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class WalletService {
    private final WalletRepository repository;

    public WalletService(WalletRepository repository) {
        this.repository = repository;
    }

    public WalletDTO cadastar(WalletForm form){
        Wallet wallet = new Wallet(form);
        wallet.setWalletValue(new BigDecimal("0.00"));
        repository.save(wallet);
        return new WalletDTO(wallet);
    }
}
