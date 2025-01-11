package com.mercaline.error.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The Class DatabaseConnectionException.
 */
@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
public class DatabaseConnectionException extends RuntimeException {

  /**
   * Instantiates a new database connection exception.
   */
  public DatabaseConnectionException() {
        super("No se puede conectar a la base de datos, intentelo más tarde.");
    }
}
