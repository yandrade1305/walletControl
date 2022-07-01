package br.com.tracefinance.walletControl.controller;

import br.com.tracefinance.walletControl.domain.form.WalletForm;
import br.com.tracefinance.walletControl.exceptions.CarteiraDuplicadaException;
import br.com.tracefinance.walletControl.exceptions.CarteiraNaoEncontradaException;
import br.com.tracefinance.walletControl.exceptions.NomeDaCarteiraNaoPodeSerNuloException;
import br.com.tracefinance.walletControl.service.WalletService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        } catch (CarteiraDuplicadaException | NomeDaCarteiraNaoPodeSerNuloException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping(path = "/{idWallet}/limits")
    public ResponseEntity<?> detalharLimite(@PathVariable Long idWallet){
        try {
            return ResponseEntity.ok(service.obterPor(idWallet));
        } catch (CarteiraNaoEncontradaException ex) {
            return  ResponseEntity.badRequest().body(ex.getMessage());
        }

    }
}
