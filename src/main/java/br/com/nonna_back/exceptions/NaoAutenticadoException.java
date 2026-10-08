package br.com.nonna_back.exceptions;

/** 401: a rota exige login e não veio nenhum token válido. */
public class NaoAutenticadoException extends RuntimeException {
    public NaoAutenticadoException(String message) { super(message); }
}
