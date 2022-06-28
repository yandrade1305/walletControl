package br.com.tracefinance.walletControl.domain.form;

import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Getter
public class PaymentForm {
    private BigDecimal amount;
    private LocalDate paymentDate;
}
