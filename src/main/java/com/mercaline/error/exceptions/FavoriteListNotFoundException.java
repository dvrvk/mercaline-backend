package com.mercaline.error.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The Class FavoriteListNotFoundException.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class FavoriteListNotFoundException extends RuntimeException {

    /**
     * Instantiates a new favorite list not found exception.
     */
    public FavoriteListNotFoundException() {
        super("No se puede encontrar la lista seleccionada.");
    }
    
    /**
     * Instantiates a new favorite list not found exception.
     *
     * @param message the message
     */
    public FavoriteListNotFoundException(String message) {
        super(message);
    }
}
