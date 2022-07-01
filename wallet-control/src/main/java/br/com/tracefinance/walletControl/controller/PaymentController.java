package br.com.tracefinance.walletControl.controller;

import br.com.tracefinance.walletControl.domain.form.PaymentForm;
import br.com.tracefinance.walletControl.exceptions.*;
import br.com.tracefinance.walletControl.service.WalletService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("wallets")
public class PaymentController {
    private final WalletService service;

    public PaymentController(WalletService service) {
        this.service = service;
    }

    @PostMapping(path = "/{idWallet}/payments")
    public ResponseEntity<?> realizarPagamento(@PathVariable Long idWallet, @RequestBody PaymentForm form){
        try {
            service.realizarPagamento(idWallet, form);
            return ResponseEntity.ok().build();
        } catch (CarteiraNaoEncontradaException |
                PagamentoMaiorQueLimiteException |
                PagamentoMaiorQueMilException |
                LimiteDiurnoExcedidoException |
                LimiteNoturnoExcedidoException ex) {
            return  ResponseEntity.badRequest().body(ex.getMessage());
        }

    }
}
