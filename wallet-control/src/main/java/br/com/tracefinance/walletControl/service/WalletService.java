package br.com.tracefinance.walletControl.service;

import br.com.tracefinance.walletControl.domain.dto.WalletDTO;
import br.com.tracefinance.walletControl.domain.dto.WalletLimitDTO;
import br.com.tracefinance.walletControl.domain.entity.Wallet;
import br.com.tracefinance.walletControl.domain.form.PaymentForm;
import br.com.tracefinance.walletControl.domain.form.WalletForm;
import br.com.tracefinance.walletControl.domain.repository.WalletRepository;
import br.com.tracefinance.walletControl.exceptions.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.Optional;

import static br.com.tracefinance.walletControl.domain.PaymentUtils.*;

@Service
public class WalletService {
    private final WalletRepository repository;
    private final BigDecimal MIL = new BigDecimal("1000.00");
    private final BigDecimal CINCO_MIL = new BigDecimal("5000.00");
    private final BigDecimal QUATRO_MIL = new BigDecimal("4000.00");

    public WalletService(WalletRepository repository) {
        this.repository = repository;
    }

    public WalletDTO cadastar(WalletForm form){
        if (form.getOwnerName() != null && !form.getOwnerName().isEmpty()){
            Optional<Wallet> optWallet = repository.findByOwnerName(form.getOwnerName());
            if (!optWallet.isPresent()) {
                Wallet wallet = new Wallet(form);
                repository.save(wallet);
                return new WalletDTO(wallet);
            } else {
                throw new CarteiraDuplicadaException("Carteira com nome já cadastrado!");
            }
        } else {
            throw new NomeDaCarteiraNaoPodeSerNuloException("Nome da carteira não pode ser nulo ou não pode ser vazio");
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
        if(optWallet.isPresent()){
            BigDecimal weekendLimit = optWallet.get().getWeekendPayment();
            BigDecimal dayTimeLimit = optWallet.get().getDayTimePayment();
            BigDecimal nocturnalLimit = optWallet.get().getNocturnalPayment();
            BigDecimal limit = optWallet.get().getWalletValue();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            LocalDateTime paymentDate = LocalDateTime.parse(form.getPaymentDate(), formatter);
            LocalDate localDateForm = paymentDate.toLocalDate();
            LocalDate localDateLastPayment = optWallet.get().getLastPayment().toLocalDate();
            int compareDates = localDateForm.compareTo(localDateLastPayment);

            if(compareDates == 0){
                compareLimit(form, optWallet, weekendLimit, dayTimeLimit, nocturnalLimit, limit, paymentDate);
            } else {
                weekendLimit = MIL;
                optWallet.get().setWeekendPayment(weekendLimit);
                dayTimeLimit = QUATRO_MIL;
                optWallet.get().setDayTimePayment(dayTimeLimit);
                nocturnalLimit = MIL;
                optWallet.get().setNocturnalPayment(nocturnalLimit);
                limit = CINCO_MIL;
                optWallet.get().setWalletValue(limit);
                compareLimit(form, optWallet, weekendLimit, dayTimeLimit, nocturnalLimit, limit, paymentDate);
            }
            optWallet.get().setLastPayment(paymentDate);
            repository.save(optWallet.get());
            return "Pagamento Realizado com sucesso!";
        }
        throw new CarteiraNaoEncontradaException("Carteira não encontrada para o id: " + idWallet);
    }
}
