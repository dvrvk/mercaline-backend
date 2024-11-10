package com.mercaline.error.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class DirectoryDeletionException extends RuntimeException {

    public DirectoryDeletionException() {
        super("No se pudo eliminar el directorio del usuario. El usuario no se pudo eliminar. Intentalo más tarde.");
    }

    public DirectoryDeletionException(String message) {
        super(message);
    }
}
