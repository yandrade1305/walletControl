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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
        // Primeiro verifica se o limite é do mesmo dia, se não for atualiza
        // Segundo verifica se o pagamento é inferior a 1000
        // Terceiro verifica dia da semana
        // Quarto verifica se o pagamento é diurno ou noturno
        // quarto verifica dia da semana
        Optional<Wallet> optWallet = repository.findById(idWallet);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDateTime paymentDate = LocalDateTime.parse(form.getPaymentDate(), formatter);
        if(paymentDate.isEqual(optWallet.get().getLastPayment())){
            System.out.println("Mesmo dia");
            int i = form.getAmount().compareTo(new BigDecimal("1000.00"));
            if (i != 1){
                System.out.println("Valor correto");
                if ((paymentDate.getDayOfWeek().equals("SATURDAY")||paymentDate.getDayOfWeek().equals("SUNDAY"))){
                    System.out.println("Realiza Operação de Fim de semana");
                    if (paymentDate.getHour() >= 6 && paymentDate.getHour() < 18){
                        System.out.println("Realiza a operação diurna");
                    } else {
                        System.out.println("Realiza operação noturna");
                    }
                } else {
                    System.out.println("Realiza Operação de dia útil");
                    if (paymentDate.getHour() >= 6 && paymentDate.getHour() < 18){
                        System.out.println("Realiza a operação diurna");
                    } else {
                        System.out.println("Realiza operação noturna");
                    }
                }
            } else {
                System.out.println("Valor errado");
            }
        } else {
            System.out.println("É diferente, logo atualizo o valor");
            // Atualizo
            int i = form.getAmount().compareTo(new BigDecimal("1000.00"));
            if (i != 1){
                System.out.println("Valor correto");
                if ((paymentDate.getDayOfWeek().equals("SATURDAY")||paymentDate.getDayOfWeek().equals("SUNDAY"))){
                    System.out.println("Realiza Operação de Fim de semana");
                    if (paymentDate.getHour() >= 6 && paymentDate.getHour() < 18){
                        System.out.println("Realiza a operação diurna");
                    } else {
                        System.out.println("Realiza operação noturna");
                    }
                } else {
                    System.out.println("Realiza Operação de dia útil");
                    if (paymentDate.getHour() >= 6 && paymentDate.getHour() < 18){
                        System.out.println("Realiza a operação diurna");
                    } else {
                        System.out.println("Realiza operação noturna");
                    }
                }
            } else {
                System.out.println("Valor errado");
            }

        }

        

//        BigDecimal subtract = optWallet.get().getWalletValue().subtract(form.getAmount());
//        optWallet.get().setWalletValue(subtract);
//        repository.save(optWallet.get());


        return "Pagamento Realizado com sucesso!";
    }
}
