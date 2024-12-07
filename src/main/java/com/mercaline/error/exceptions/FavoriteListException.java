package com.mercaline.error.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class FavoriteListException extends RuntimeException {

    public FavoriteListException() {
        super("No puedes tener dos o más listas con el mismo nombre.");
    }
}
