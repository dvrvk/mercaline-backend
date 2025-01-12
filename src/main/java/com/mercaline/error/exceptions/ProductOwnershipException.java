package com.mercaline.error.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The Class ProductOwnershipException.
 */
@ResponseStatus(HttpStatus.FORBIDDEN)
public class ProductOwnershipException extends RuntimeException {

    /**
     * Instantiates a new product ownership exception.
     */
    public ProductOwnershipException() {
        super("No tienes permiso para modificar este producto.");
    }

    /**
     * Instantiates a new product ownership exception.
     *
     * @param message the message
     */
    public ProductOwnershipException(String message) {
        super(message);
    }
}
