package br.com.tracefinance.walletControl.controller;

import br.com.tracefinance.walletControl.domain.form.PaymentForm;
import br.com.tracefinance.walletControl.exceptions.CarteiraNaoEncontradaException;
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
            return ResponseEntity.ok(service.realizarPagamento(idWallet, form));
        } catch (CarteiraNaoEncontradaException ex) {
            return  ResponseEntity.badRequest().body(ex.getMessage());
        }

    }
}
