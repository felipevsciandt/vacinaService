package com.felipe.arqsoftware.demo.controller.exception;

import com.felipe.arqsoftware.demo.service.exceptions.AccountNotFoundException;
import com.felipe.arqsoftware.demo.service.exceptions.SaldoInsuficienteException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(SaldoInsuficienteException.class)
    public ResponseEntity<StandardError> saldoInsuficienteException(SaldoInsuficienteException e, HttpServletRequest request){
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError err = new StandardError();
        err.setTimestamp(Instant.now());
        err.setStatus(status.value());
        err.setError("Operacao Invalida: Saldo insuficiente");
        err.setMessage(e.getMessage());
        err.setPath(request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<StandardError> accountNotFoundException(AccountNotFoundException e, HttpServletRequest request){
        HttpStatus status = HttpStatus.NO_CONTENT;
        StandardError err = new StandardError();
        err.setTimestamp(Instant.now());
        err.setStatus(status.value());
        err.setError("Erro: Id não cadastrado");
        err.setMessage(e.getMessage());
        err.setPath(request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }
}
