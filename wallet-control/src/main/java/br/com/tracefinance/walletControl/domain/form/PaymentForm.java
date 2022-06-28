package br.com.tracefinance.walletControl.domain.form;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class PaymentForm {
    private BigDecimal amount;
    private String paymentDate;
}
