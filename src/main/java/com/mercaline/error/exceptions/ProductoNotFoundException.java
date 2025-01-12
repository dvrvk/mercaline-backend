package com.mercaline.error.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The Class ProductoNotFoundException.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProductoNotFoundException extends RuntimeException{

    /**
     * Instantiates a new producto not found exception.
     *
     * @param id the id
     */
    public ProductoNotFoundException(Long id) {
        super("No se puede encontrar el producto con el ID: " + id);
    }

    /**
     * Instantiates a new producto not found exception.
     */
    public ProductoNotFoundException() {
        super("No se pueden encontrar productos");
    }

}
