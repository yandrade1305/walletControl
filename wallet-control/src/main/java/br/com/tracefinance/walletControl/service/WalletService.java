package br.com.tracefinance.walletControl.service;

import br.com.tracefinance.walletControl.domain.dto.WalletDTO;
import br.com.tracefinance.walletControl.domain.dto.WalletLimitDTO;
import br.com.tracefinance.walletControl.domain.entity.Wallet;
import br.com.tracefinance.walletControl.domain.form.PaymentForm;
import br.com.tracefinance.walletControl.domain.form.WalletForm;
import br.com.tracefinance.walletControl.domain.repository.WalletRepository;
import br.com.tracefinance.walletControl.exceptions.CarteiraDuplicadaException;
import br.com.tracefinance.walletControl.exceptions.CarteiraNaoEncontradaException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class WalletService {
    private final WalletRepository repository;

    public WalletService(WalletRepository repository) {
        this.repository = repository;
    }

    public WalletDTO cadastar(WalletForm form){
        Optional<Wallet> optWallet = repository.findByOwnerName(form.getOwnerName());
        if (!optWallet.isPresent()) {
            Wallet wallet = new Wallet(form);
            wallet.setWalletValue(BigDecimal.ZERO);
            repository.save(wallet);
            return new WalletDTO(wallet);
        } else {
            throw new CarteiraDuplicadaException("Carteira com nome já cadastrado!");
        }
    }

    public WalletLimitDTO obterPor(Long idWallet){
        Optional<Wallet> optWallet = repository.findById(idWallet);
        if (optWallet.isPresent()){
            WalletLimitDTO walletLimitDTO = new WalletLimitDTO(optWallet.get().getWalletValue());
            return walletLimitDTO;
        }
        throw new CarteiraNaoEncontradaException("Carteira não encontrada para o id: " + idWallet);
    }

    public String realizarPagamento(Long idWallet, PaymentForm form){
        Optional<Wallet> optWallet = repository.findById(idWallet);
        BigDecimal subtract = optWallet.get().getWalletValue().subtract(form.getAmount());
        optWallet.get().setWalletValue(subtract);
        repository.save(optWallet.get());
        return "Pagamento Realizado com sucesso!";
    }
}
