package br.com.tracefinance.walletControl.domain;

import br.com.tracefinance.walletControl.domain.entity.Wallet;
import br.com.tracefinance.walletControl.domain.form.PaymentForm;

import java.math.BigDecimal;
import java.util.Optional;

public abstract class  PaymentUtils {
    public static int getCompareLimit(BigDecimal limit, BigDecimal form) {
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
        businessDay(optWallet, form);
    }
}
