package com.mercaline.error.exceptions;

/**
 * The Class ProductUnauthorizedAccessException.
 */
public class ProductUnauthorizedAccessException extends RuntimeException {

    /**
     * Instantiates a new product unauthorized access exception.
     *
     * @param id the id
     */
    public ProductUnauthorizedAccessException(Long id) {
        super("No tienes permiso para modificar el producto con id: " + id);
    }

    /**
     * Instantiates a new product unauthorized access exception.
     */
    public ProductUnauthorizedAccessException() {
        super("Acción no autorizada.");
    }
}
