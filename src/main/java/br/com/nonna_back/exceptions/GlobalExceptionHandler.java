package br.com.nonna_back.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroResponse> handleValidationErrors(MethodArgumentNotValidException ex) {
        List<String> erros = ex.getBindingResult().getFieldErrors()
                .stream().map(FieldError::getDefaultMessage).collect(Collectors.toList());
        return new ResponseEntity<>(new ErroResponse(HttpStatus.BAD_REQUEST.value(), erros), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<ErroResponse> handleNotFound(RecursoNaoEncontradoException ex) {
        return new ResponseEntity<>(new ErroResponse(HttpStatus.NOT_FOUND.value(), List.of(ex.getMessage())), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RegraNegocioException.class)
    public ResponseEntity<ErroResponse> handleRegraNegocio(RegraNegocioException ex) {
        return new ResponseEntity<>(new ErroResponse(HttpStatus.BAD_REQUEST.value(), List.of(ex.getMessage())), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NaoAutenticadoException.class)
    public ResponseEntity<ErroResponse> handleNaoAutenticado(NaoAutenticadoException ex) {
        return new ResponseEntity<>(new ErroResponse(HttpStatus.UNAUTHORIZED.value(), List.of(ex.getMessage())), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(NaoAutorizadoException.class)
    public ResponseEntity<ErroResponse> handleNaoAutorizado(NaoAutorizadoException ex) {
        return new ResponseEntity<>(new ErroResponse(HttpStatus.FORBIDDEN.value(), List.of(ex.getMessage())), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(CredenciaisInvalidasException.class)
    public ResponseEntity<ErroResponse> handleCredenciaisInvalidas(CredenciaisInvalidasException ex) {
        return new ResponseEntity<>(new ErroResponse(HttpStatus.UNAUTHORIZED.value(), List.of(ex.getMessage())), HttpStatus.UNAUTHORIZED);
    }
}
