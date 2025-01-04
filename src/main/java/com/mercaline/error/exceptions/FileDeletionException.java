package com.mercaline.error.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The Class FileDeletionException.
 */
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class FileDeletionException extends RuntimeException {

    /**
     * Instantiates a new file deletion exception.
     */
    public FileDeletionException() {
        super("No se pudo eliminar el archivo del usuario. No se pudo eliminar el usuario. Intentaló más tarde. ");
    }

    /**
     * Instantiates a new file deletion exception.
     *
     * @param message the message
     */
    public FileDeletionException(String message) {
        super(message);
    }
}
