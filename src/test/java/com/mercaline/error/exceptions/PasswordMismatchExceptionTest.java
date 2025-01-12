package com.mercaline.error.exceptions;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PasswordMismatchExceptionTest {

    @Test
    void testDefaultConstructor() {
        PasswordMismatchException exception = assertThrows(PasswordMismatchException.class, () -> {
            throw new PasswordMismatchException();
        });

        assertEquals("La contraseña no es correcta. Vuelve a intentarlo.", exception.getMessage());
    }

    @Test
    void testParameterizedConstructor() {
        String customMessage = "Custom error message";
        PasswordMismatchException exception = assertThrows(PasswordMismatchException.class, () -> {
            throw new PasswordMismatchException(customMessage);
        });

        assertEquals(customMessage, exception.getMessage());
    }

}
