package br.com.tracefinance.walletControl.exceptions;

public class NomeDaCarteiraNaoPodeSerNuloException extends RuntimeException{
    private final String mensagem;

    public NomeDaCarteiraNaoPodeSerNuloException(String mensagem){
        super(mensagem);
        this.mensagem = mensagem;
    }
}
