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

import static br.com.tracefinance.walletControl.domain.PaymentUtils.*;

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
        // Antes de tudo verificar se ele tem esse dinheiro disponível
        // Primeiro verifica se o limite é do mesmo dia, se não for atualiza
        // Segundo verifica se o pagamento é inferior a 1000
        // Terceiro verifica dia da semana
        // Quarto verifica se o pagamento é diurno ou noturno
        // quarto verifica dia da semana
        Optional<Wallet> optWallet = repository.findById(idWallet);
        BigDecimal weekendLimit = optWallet.get().getWeekendPayment();
        BigDecimal dayTimeLimit = optWallet.get().getDayTimePayment();
        BigDecimal nocturnalLimit = optWallet.get().getNocturnalPayment();
        BigDecimal limit = optWallet.get().getWalletValue();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDateTime paymentDate = LocalDateTime.parse(form.getPaymentDate(), formatter);
        int compareLimit = getCompareLimit(form.getAmount(), limit);
        if (compareLimit == -1 || compareLimit == 0){
            if(paymentDate.isEqual(optWallet.get().getLastPayment())){
                System.out.println("Mesmo dia");
                int compareAmount = getCompareLimit(form.getAmount(), new BigDecimal("1000.00"));
                if (compareAmount == -1 || compareAmount == 0){
                    System.out.println("Valor correto");
                    if ((paymentDate.getDayOfWeek().equals("SATURDAY")||paymentDate.getDayOfWeek().equals("SUNDAY"))){
                        int compareLimitWeekend = getCompareLimit(form.getAmount(), weekendLimit);
                        if (compareLimitWeekend == -1 || compareLimitWeekend == 0){
                            System.out.println("Realiza Operação de Fim de semana");
                            weekendPayment(weekendLimit, form, optWallet);
                            if (paymentDate.getHour() >= 6 && paymentDate.getHour() < 18){
                                System.out.println("Realiza a operação diurna");
                                dayTimePayment(dayTimeLimit, form, optWallet);
                            } else {
                                System.out.println("Realiza operação noturna");
                                nocturnalPayment(nocturnalLimit, form, optWallet);
                            }
                        } else {
                            return "Limite Excedido";
                        }
                    } else {
                        System.out.println("Realiza Operação de dia útil");
                        businessDay(optWallet, form);
                        if (paymentDate.getHour() >= 6 && paymentDate.getHour() < 18){
                            System.out.println("Realiza a operação diurna");
                            dayTimePayment(dayTimeLimit, form, optWallet);
                        } else {
                            System.out.println("Realiza operação noturna");
                            nocturnalPayment(nocturnalLimit, form, optWallet);
                        }
                    }
                } else {
                    System.out.println("Valor errado");
                }
            } else {
                System.out.println("É diferente, logo atualizo o valor");
                weekendLimit = new BigDecimal("1000.00");
                dayTimeLimit = new BigDecimal("4000.00");
                nocturnalLimit = new BigDecimal("1000.00");
                int i = getCompareLimit(form.getAmount(), new BigDecimal("1000.00"));
                if (i != 1){
                    System.out.println("Valor correto");
                    if ((paymentDate.getDayOfWeek().equals("SATURDAY")||paymentDate.getDayOfWeek().equals("SUNDAY"))){
                        System.out.println("Realiza Operação de Fim de semana");
                        weekendPayment(weekendLimit, form, optWallet);
                        if (paymentDate.getHour() >= 6 && paymentDate.getHour() < 18){
                            System.out.println("Realiza a operação diurna");
                            dayTimePayment(dayTimeLimit, form, optWallet);
                        } else {
                            System.out.println("Realiza operação noturna");
                            nocturnalPayment(nocturnalLimit, form, optWallet);
                        }
                    } else {
                        System.out.println("Realiza Operação de dia útil");
                        businessDay(optWallet, form);
                        if (paymentDate.getHour() >= 6 && paymentDate.getHour() < 18){
                            System.out.println("Realiza a operação diurna");
                            dayTimePayment(dayTimeLimit, form, optWallet);
                        } else {
                            System.out.println("Realiza operação noturna");
                            nocturnalPayment(nocturnalLimit, form, optWallet);
                        }
                    }
                } else {
                    System.out.println("Valor errado");
                }
            }
            //Salvo
            repository.save(optWallet.get());
        } else {
            return "Limite excedido";
        }
        return "Pagamento Realizado com sucesso!";
    }
}
