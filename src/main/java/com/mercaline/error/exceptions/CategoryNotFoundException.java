package com.mercaline.error.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The Class CategoryNotFoundException.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class CategoryNotFoundException extends RuntimeException {

    /**
     * Instantiates a new category not found exception.
     */
    public CategoryNotFoundException() {
        super("No se puede encontrar la categoría seleccionada.");
    }
}
