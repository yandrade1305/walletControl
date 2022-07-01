package br.com.tracefinance.walletControl.exceptions;

public class LimiteDiurnoExcedidoException extends RuntimeException {
    private final String mensagem;

    public LimiteDiurnoExcedidoException(String mensagem){
        super(mensagem);
        this.mensagem = mensagem;
    }
}
