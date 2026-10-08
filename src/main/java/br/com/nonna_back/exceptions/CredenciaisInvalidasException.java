package br.com.nonna_back.exceptions;

/** 401: e-mail ou senha não batem no login. Mensagem genérica de propósito. */
public class CredenciaisInvalidasException extends RuntimeException {
    public CredenciaisInvalidasException(String message) { super(message); }
}
