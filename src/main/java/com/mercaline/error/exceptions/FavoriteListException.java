package com.mercaline.error.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The Class FavoriteListException.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class FavoriteListException extends RuntimeException {

    /**
     * Instantiates a new favorite list exception.
     */
    public FavoriteListException() {
        super("No puedes tener dos o más listas con el mismo nombre.");
    }
}
