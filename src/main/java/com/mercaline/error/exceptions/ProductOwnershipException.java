package com.mercaline.error.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class ProductOwnershipException extends RuntimeException {

    public ProductOwnershipException() {
        super("No tienes permiso para modificar este producto.");
    }

    public ProductOwnershipException(String message) {
        super(message);
    }
}
