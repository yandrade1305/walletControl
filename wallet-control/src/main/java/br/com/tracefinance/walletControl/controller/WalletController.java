package br.com.tracefinance.walletControl.controller;

import br.com.tracefinance.walletControl.domain.form.WalletForm;
import br.com.tracefinance.walletControl.service.WalletService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("wallets")
public class WalletController {
    private final WalletService service;

    public WalletController(WalletService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> cadastrarCarteira(@RequestBody WalletForm form){
        try {
            return ResponseEntity.created(URI.create("")).body(service.cadastar(form));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }

}
