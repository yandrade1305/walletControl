package br.com.tracefinance.walletControl.exceptions;

public class LimiteNoturnoExcedidoException extends RuntimeException {
    private final String mensagem;

    public LimiteNoturnoExcedidoException(String mensagem){
        super(mensagem);
        this.mensagem = mensagem;
    }
}
