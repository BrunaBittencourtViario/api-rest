package com.compassuol.apiproduto.config.exception;

import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ResourceExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionResponse(HttpStatus.BAD_REQUEST.value(), "Bad Request"));
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionResponse(HttpStatus.BAD_REQUEST.value(), "O campo nome é obrigatório"));
    }

    @ExceptionHandler (ResourceNotFoundException.class)
    public ResponseEntity <ExceptionResponse> resourceNotFound(ResourceNotFoundException e, HttpServletRequest request) {
        String message = "Not Found";
        HttpStatus status = HttpStatus.NOT_FOUND;
        ExceptionResponse er = new ExceptionResponse(status.value(), e.getMessage());
        return ResponseEntity.status(status).body(er);
    }

    @ExceptionHandler (value = {InvalidDataAccessApiUsageException.class, IllegalStateException.class})
    public ResponseEntity <ExceptionResponse> resourceInternalServer(RuntimeException e, HttpServletRequest request) {
        String message = "Internal Server Error";
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ExceptionResponse er = new ExceptionResponse(status.value(), message);
        return ResponseEntity.status(status).body(er);
    }



}
