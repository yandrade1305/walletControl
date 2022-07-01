package br.com.tracefinance.walletControl.domain;

import br.com.tracefinance.walletControl.domain.entity.Wallet;
import br.com.tracefinance.walletControl.domain.form.PaymentForm;
import br.com.tracefinance.walletControl.exceptions.LimiteDiurnoExcedidoException;
import br.com.tracefinance.walletControl.exceptions.LimiteNoturnoExcedidoException;
import br.com.tracefinance.walletControl.exceptions.PagamentoMaiorQueLimiteException;
import br.com.tracefinance.walletControl.exceptions.PagamentoMaiorQueMilException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

public abstract class  PaymentUtils {
    public static int getCompareLimit(BigDecimal form, BigDecimal limit) {
        int compareLimit = limit.compareTo(form);
        return compareLimit;
    }

    public static void businessDay(Optional<Wallet> optWallet, PaymentForm form) {
        BigDecimal newValue = optWallet.get().getWalletValue().subtract(form.getAmount());
        optWallet.get().setWalletValue(newValue);
    }

    public static void nocturnalPayment(BigDecimal nocturnalLimit, PaymentForm form, Optional<Wallet> optWallet) {
        BigDecimal newLimitNocturnal = nocturnalLimit.subtract(form.getAmount());
        optWallet.get().setNocturnalPayment(newLimitNocturnal);
    }

    public static void dayTimePayment(BigDecimal dayTimeLimit, PaymentForm form, Optional<Wallet> optWallet) {
        BigDecimal newLimitDaytime = dayTimeLimit.subtract(form.getAmount());
        optWallet.get().setDayTimePayment(newLimitDaytime);
    }

    public static void weekendPayment(BigDecimal weekendLimit, PaymentForm form, Optional<Wallet> optWallet) {
        BigDecimal newLimit = weekendLimit.subtract(form.getAmount());
        optWallet.get().setWeekendPayment(newLimit);
        optWallet.get().setWalletValue(newLimit);
    }

    public static void validatePayment(PaymentForm form,
                                       Optional<Wallet> optWallet,
                                       BigDecimal weekendLimit,
                                       BigDecimal dayTimeLimit,
                                       BigDecimal nocturnalLimit,
                                       LocalDateTime paymentDate,
                                       int compareAmount) {
        if (compareAmount != 1){
            if (paymentDate.getDayOfWeek().getValue() == 6 || paymentDate.getDayOfWeek().getValue() == 7){
                optWallet.get().setWalletValue(weekendLimit);
                PaymentUtils.weekendPayment(weekendLimit, form, optWallet);
            } else {
                if (paymentDate.getHour() >= 6 && paymentDate.getHour() < 18){
                    int compareDayTimePayment = optWallet.get().getDayTimePayment().compareTo(BigDecimal.ZERO);
                    if (compareDayTimePayment == 1 ){
                        PaymentUtils.dayTimePayment(dayTimeLimit, form, optWallet);
                    } else {
                        throw new LimiteDiurnoExcedidoException("Limite diurno excedido");
                    }
                } else {
                    int compareNocturnalPayment= optWallet.get().getNocturnalPayment().compareTo(BigDecimal.ZERO);
                    if (compareNocturnalPayment == 1){
                        PaymentUtils.nocturnalPayment(nocturnalLimit, form, optWallet);
                    } else {
                        throw new LimiteNoturnoExcedidoException("Limite noturno excedido");
                    }
                }
                PaymentUtils.businessDay(optWallet, form);
            }
        } else {
            throw  new PagamentoMaiorQueMilException("Pagamento negado! (Valor à ser pago é superior a R$: 1000,00)");
        }
    }

    public static void compareLimit(PaymentForm form,
                                    Optional<Wallet> optWallet,
                                    BigDecimal weekendLimit,
                                    BigDecimal dayTimeLimit,
                                    BigDecimal nocturnalLimit,
                                    BigDecimal limit,
                                    LocalDateTime paymentDate) {
        int compareLimit = getCompareLimit(form.getAmount(), limit);
        if (compareLimit == 1 || compareLimit == 0){
            int compareAmount = getCompareLimit(new BigDecimal("1000.00"), form.getAmount());
            validatePayment(form, optWallet, weekendLimit, dayTimeLimit, nocturnalLimit, paymentDate, compareAmount);
        } else {
            throw new PagamentoMaiorQueLimiteException("Pagamento negado! (valor a ser pago é maior que o disponível)");
        }
    }
}
