package com.tmt.pos.mypos.config;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ AccessDeniedException.class })
    public ResponseEntity<Object> handleAccessDeniedException(
            Exception ex, WebRequest request) {
        return new ResponseEntity<Object>(
                "Access denied to the resource being accessed!", new HttpHeaders(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({ AuthenticationException.class })
    public ResponseEntity<Object> handleAuthenticationException(
            Exception ex, WebRequest request) {
        return new ResponseEntity<Object>(
                "Authentication failed!", new HttpHeaders(), HttpStatus.FORBIDDEN);
    }
}
