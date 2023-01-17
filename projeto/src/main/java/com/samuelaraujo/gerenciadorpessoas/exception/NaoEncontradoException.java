package com.samuelaraujo.gerenciadorpessoas.exception;

public class NaoEncontradoException extends RuntimeException {

    public NaoEncontradoException() {
    }

    public NaoEncontradoException(String message) {
        super(message);
    }

    public NaoEncontradoException(String message, Throwable cause) {
        super(message, cause);
    }

    public NaoEncontradoException(Throwable cause) {
        super(cause);
    }
}
