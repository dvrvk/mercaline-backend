package com.mercaline.error.exceptions;

/**
 * The Class PasswordMismatchException.
 */
public class PasswordMismatchException extends RuntimeException {

    /**
     * Instantiates a new password mismatch exception.
     */
    public PasswordMismatchException() {
      super("La contraseña no es correcta. Vuelve a intentarlo.");
    }

    /**
     * Instantiates a new password mismatch exception.
     *
     * @param message the message
     */
    public PasswordMismatchException(String message) {
        super(message);
    }
}
