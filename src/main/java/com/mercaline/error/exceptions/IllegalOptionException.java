package com.mercaline.error.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class IllegalOptionException extends RuntimeException {
    public IllegalOptionException(String message) {
        super(message);
    }

    public IllegalOptionException() {
        super("Opción seleccionada para la actualización de las imagenes no válida. Elige una opción válida de las opciones disponibles.");
    }
}
