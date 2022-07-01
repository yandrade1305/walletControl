package br.com.tracefinance.walletControl.exceptions;

public class PagamentoMaiorQueMilException extends RuntimeException {
    private final String mensagem;

    public PagamentoMaiorQueMilException(String mensagem){
        super(mensagem);
        this.mensagem = mensagem;
    }
}
