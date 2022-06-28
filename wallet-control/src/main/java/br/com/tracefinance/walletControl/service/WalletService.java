package br.com.tracefinance.walletControl.service;

import br.com.tracefinance.walletControl.domain.entity.Wallet;
import br.com.tracefinance.walletControl.domain.form.WalletForm;
import br.com.tracefinance.walletControl.domain.repository.WalletRepository;

public class WalletService {
    private final WalletRepository repository;

    public WalletService(WalletRepository repository) {
        this.repository = repository;
    }

    public void cadastar(WalletForm form){
        Wallet wallet = new Wallet(form);
        repository.save(wallet);
    }
}
