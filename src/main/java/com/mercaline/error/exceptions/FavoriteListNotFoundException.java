package com.mercaline.error.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class FavoriteListNotFoundException extends RuntimeException {

    public FavoriteListNotFoundException() {
        super("No se puede encontrar la lista seleccionada.");
    }
    
    public FavoriteListNotFoundException(String message) {
        super(message);
    }
}
