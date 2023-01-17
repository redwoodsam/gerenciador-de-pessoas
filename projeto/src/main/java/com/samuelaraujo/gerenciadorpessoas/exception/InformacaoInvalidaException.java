package com.samuelaraujo.gerenciadorpessoas.exception;

public class InformacaoInvalidaException extends RuntimeException {

    public InformacaoInvalidaException() {}

    public InformacaoInvalidaException(String message) {
        super(message);
    }

    public InformacaoInvalidaException(String message, Throwable cause) {
        super(message, cause);
    }

    public InformacaoInvalidaException(Throwable cause) {
        super(cause);
    }

}
