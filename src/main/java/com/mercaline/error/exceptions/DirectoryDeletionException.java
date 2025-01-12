package com.mercaline.error.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The Class DirectoryDeletionException.
 */
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class DirectoryDeletionException extends RuntimeException {

    /**
     * Instantiates a new directory deletion exception.
     */
    public DirectoryDeletionException() {
        super("No se pudo eliminar el directorio del usuario. El usuario no se pudo eliminar. Intentalo más tarde.");
    }

    /**
     * Instantiates a new directory deletion exception.
     *
     * @param message the message
     */
    public DirectoryDeletionException(String message) {
        super(message);
    }
}
