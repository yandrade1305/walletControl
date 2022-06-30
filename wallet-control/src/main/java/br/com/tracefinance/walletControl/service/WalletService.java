package br.com.tracefinance.walletControl.service;

import br.com.tracefinance.walletControl.domain.PaymentUtils;
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
        Optional<Wallet> optWallet = repository.findById(idWallet);
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
            System.out.println("Mesmo dia");
            int compareLimit = getCompareLimit(form.getAmount(), limit);
            if (compareLimit == 1 || compareLimit == 0){
                System.out.println("Pagamento com limite permitido");
                int compareAmount = getCompareLimit(MIL, form.getAmount());
                System.out.println(paymentDate.getDayOfWeek());
                if (compareAmount != 1){
                    if (paymentDate.getDayOfWeek().getValue() == 6 || paymentDate.getDayOfWeek().getValue() == 7){
                        System.out.println("Fim de semana");
                        optWallet.get().setWalletValue(weekendLimit);
                        if (paymentDate.getHour() >= 6 && paymentDate.getHour() < 18){
                            System.out.println("Pagamento diurno");
                            int compareDayTimePayment = optWallet.get().getDayTimePayment().compareTo(BigDecimal.ZERO);
                            System.out.println("Valor Banco: " + optWallet.get().getDayTimePayment());
                            System.out.println(optWallet.get().getDayTimePayment().compareTo(BigDecimal.ZERO));
                            if (compareDayTimePayment == 1){
                                PaymentUtils.dayTimePayment(dayTimeLimit, form, optWallet);
                            } else {
                                return "Limite diurno excedido!";
                            }
                        } else {
                            System.out.println("Pagamento noturno");
                            int compareNocturnalPayment= optWallet.get().getNocturnalPayment().compareTo(BigDecimal.ZERO);
                            if (compareNocturnalPayment == 1){
                                PaymentUtils.nocturnalPayment(nocturnalLimit, form, optWallet);
                            } else {
                                return "Limite noturno excedido!";
                            }
                        }
                        PaymentUtils.weekendPayment(weekendLimit, form, optWallet);
                    } else {
                        System.out.println("Dia útil");
                        if (paymentDate.getHour() >= 6 && paymentDate.getHour() < 18){
                            System.out.println("Pagamento diurno");
                            int compareDayTimePayment = optWallet.get().getDayTimePayment().compareTo(BigDecimal.ZERO);
                            if (compareDayTimePayment == 1){
                                PaymentUtils.dayTimePayment(dayTimeLimit, form, optWallet);
                            } else {
                                return "Limite diurno excedido!";
                            }
                        } else {
                            System.out.println("Pagamento noturno");
                            int compareNocturnalPayment= optWallet.get().getNocturnalPayment().compareTo(BigDecimal.ZERO);
                            if (compareNocturnalPayment == 1){
                                PaymentUtils.nocturnalPayment(nocturnalLimit, form, optWallet);
                            } else {
                                return "Limite noturno excedido!";
                            }
                        }
                        PaymentUtils.businessDay(optWallet, form);
                    }
                } else {
                    return "Pagamento negado! (Valor à ser pago é superior a R$: 1000,00)";
                }
            } else {
                return "Pagamento negado! (valor transferido é maior que o disponível)";
            }
        } else {
            System.out.println("Dia Diferente");
            weekendLimit = MIL;
            optWallet.get().setWeekendPayment(weekendLimit);
            dayTimeLimit = QUATRO_MIL;
            optWallet.get().setDayTimePayment(dayTimeLimit);
            nocturnalLimit = MIL;
            optWallet.get().setNocturnalPayment(nocturnalLimit);
            limit = CINCO_MIL;
            optWallet.get().setWalletValue(limit);
            int compareLimit = getCompareLimit(form.getAmount(), limit);
            if (compareLimit == 1 || compareLimit == 0){
                System.out.println("Pagamento com limite permitido");
                int compareAmount = getCompareLimit(MIL, form.getAmount());
                if (compareAmount != 1){
                    if (paymentDate.getDayOfWeek().getValue() == 6 || paymentDate.getDayOfWeek().getValue() == 7){
                        System.out.println("Fim de semana");
                        optWallet.get().setWalletValue(weekendLimit);
                        if (paymentDate.getHour() >= 6 && paymentDate.getHour() < 18){
                            System.out.println("Pagamento diurno");
                            int compareDayTimePayment = optWallet.get().getDayTimePayment().compareTo(BigDecimal.ZERO);
                            if (compareDayTimePayment == 1 ){
                                PaymentUtils.dayTimePayment(dayTimeLimit, form, optWallet);
                            } else {
                                return "Limite diurno excedido";
                            }
                        } else {
                            System.out.println("Pagamento noturno");
                            int compareNocturnalPayment= optWallet.get().getNocturnalPayment().compareTo(BigDecimal.ZERO);
                            if (compareNocturnalPayment == 1){
                                PaymentUtils.nocturnalPayment(nocturnalLimit, form, optWallet);
                            } else {
                                return "Limite noturno excedido";
                            }
                        }
                        PaymentUtils.weekendPayment(weekendLimit, form, optWallet);
                    } else {
                        System.out.println("Dia útil");
                        if (paymentDate.getHour() >= 6 && paymentDate.getHour() < 18){
                            System.out.println("Pagamento diurno");
                            int compareDayTimePayment = optWallet.get().getDayTimePayment().compareTo(BigDecimal.ZERO);
                            if (compareDayTimePayment == 1 ){
                                PaymentUtils.dayTimePayment(dayTimeLimit, form, optWallet);
                            } else {
                                return "Limite diurno excedido";
                            }
                        } else {
                            System.out.println("Pagamento noturno");
                            int compareNocturnalPayment= optWallet.get().getNocturnalPayment().compareTo(BigDecimal.ZERO);
                            if (compareNocturnalPayment == 1){
                                PaymentUtils.nocturnalPayment(nocturnalLimit, form, optWallet);
                            } else {
                                return "Limite noturno excedido";
                            }
                        }
                        PaymentUtils.businessDay(optWallet, form);
                    }
                } else {
                    return "Pagamento negado! (Valor à ser pago é superior a R$: 1000,00)";
                }
            } else {
                return "Pagamento negado! (valor transferido é maior que o disponível)";
            }
        }
        optWallet.get().setLastPayment(paymentDate);
        repository.save(optWallet.get());
        return "Pagamento Realizado com sucesso!";
    }
}
