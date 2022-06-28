package br.com.tracefinance.walletControl.exceptions;

import lombok.Getter;

@Getter
public class CarteiraDuplicadaException extends  RuntimeException{
    private final String mensagem;

    public CarteiraDuplicadaException(String mensagem){
        super(mensagem);
        this.mensagem = mensagem;
    }
}
