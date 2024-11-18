package com.mercaline.error.exceptions;

public class PasswordMismatchException extends RuntimeException {

    public PasswordMismatchException() {
      super("La contraseña no es correcta. Vuelve a intentarlo.");
    }

    public PasswordMismatchException(String message) {
        super(message);
    }
}
