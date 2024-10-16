package com.mercaline.error.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
public class DatabaseConnectionException extends RuntimeException {

  public DatabaseConnectionException() {
        super("No se puede conectar a la base de datos, intentelo más tarde.");
    }
}
