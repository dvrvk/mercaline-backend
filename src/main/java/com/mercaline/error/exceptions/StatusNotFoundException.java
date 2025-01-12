package com.mercaline.error.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The Class StatusNotFoundException.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class StatusNotFoundException extends RuntimeException {
    
    /**
     * Instantiates a new status not found exception.
     */
    public StatusNotFoundException() {
        super("No se puede encontrar el estado seleccionado.");
    }
}
