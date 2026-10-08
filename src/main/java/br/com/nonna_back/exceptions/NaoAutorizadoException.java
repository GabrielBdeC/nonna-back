package br.com.nonna_back.exceptions;

/** 403: o usuário está logado, mas não tem permissão para essa ação. */
public class NaoAutorizadoException extends RuntimeException {
    public NaoAutorizadoException(String message) { super(message); }
}
