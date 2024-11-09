package com.mercaline.error.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The Class ImageStorageException.
 *
 * @author Diego
 * @see RuntimeException
 * @see HttpStatus#INTERNAL_SERVER_ERROR
 *
 */
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ImageStorageException extends RuntimeException {
    /**
     * Instantiates a new ImageStorageException with a custom message.
     *
     * @param message the detail message
     */
    public ImageStorageException(String message) {
        super(message);
    }

    /**
     * Instantiates a new ImageStorageException with a default message.
     */
    public ImageStorageException() {
        super("No se puedo subir la imagen al servidor. Por favor, intenteló más tarde.");
    }
}
