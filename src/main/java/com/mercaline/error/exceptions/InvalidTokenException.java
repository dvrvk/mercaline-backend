package com.mercaline.error.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The Class InvalidTokenException.
 */
@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class InvalidTokenException extends RuntimeException {
    
    /**
     * Instantiates a new invalid token exception.
     */
    public InvalidTokenException() {
        super("El token proporcionado no es válido. Por favor, inicia sesión nuevamente.");
    }

    /**
     * Instantiates a new invalid token exception.
     *
     * @param message the message
     */
    public InvalidTokenException(String message) {
        super(message);
    }
}
