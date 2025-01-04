package com.mercaline.error.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The Class IllegalOptionException.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class IllegalOptionException extends RuntimeException {
    
    /**
     * Instantiates a new illegal option exception.
     *
     * @param message the message
     */
    public IllegalOptionException(String message) {
        super(message);
    }

    /**
     * Instantiates a new illegal option exception.
     */
    public IllegalOptionException() {
        super("Opción seleccionada para la actualización de las imagenes no válida. Elige una opción válida de las opciones disponibles.");
    }
}
