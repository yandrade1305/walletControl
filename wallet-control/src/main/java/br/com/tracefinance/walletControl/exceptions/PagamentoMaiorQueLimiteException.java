package br.com.tracefinance.walletControl.exceptions;

public class PagamentoMaiorQueLimiteException extends RuntimeException {
    private final String mensagem;

    public PagamentoMaiorQueLimiteException(String mensagem){
        super(mensagem);
        this.mensagem = mensagem;
    }
}
