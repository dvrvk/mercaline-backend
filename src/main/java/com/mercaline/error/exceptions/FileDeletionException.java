package com.mercaline.error.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class FileDeletionException extends RuntimeException {

    public FileDeletionException() {
        super("No se pudo eliminar el archivo del usuario. No se pudo eliminar el usuario. Intentaló más tarde. ");
    }

    public FileDeletionException(String message) {
        super(message);
    }
}
