package com.mercaline.error.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The Class UserNotFoundException.
 * 
 * @author caromar
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {

    /**
     * Instantiates a new user not found exception.
     */
    public UserNotFoundException() {
        super("No se puede encontrar el usuario indicado.");
    }
}
