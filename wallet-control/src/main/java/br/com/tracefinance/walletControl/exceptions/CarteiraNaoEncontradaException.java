package br.com.tracefinance.walletControl.exceptions;

public class CarteiraNaoEncontradaException extends  RuntimeException{
    private final String mensagem;

    public CarteiraNaoEncontradaException(String mensagem){
        super(mensagem);
        this.mensagem = mensagem;
    }
}
