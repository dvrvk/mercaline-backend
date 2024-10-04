package com.mercaline.error.exceptions;

public class ProductUnauthorizedAccessException extends RuntimeException {


    public ProductUnauthorizedAccessException(Long id) {
        super("No tienes permiso para modificar el producto con id: " + id);
    }

    public ProductUnauthorizedAccessException() {
        super("Acción no autorizada.");
    }
}
