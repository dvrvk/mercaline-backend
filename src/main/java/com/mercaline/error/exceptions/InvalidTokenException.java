package com.mercaline.error.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class InvalidTokenException extends RuntimeException {
    public InvalidTokenException() {
        super("El token proporcionado no es válido. Por favor, inicia sesión nuevamente.");
    }

    public InvalidTokenException(String message) {
        super(message);
    }
}
