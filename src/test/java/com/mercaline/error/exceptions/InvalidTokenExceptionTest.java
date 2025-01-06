package com.mercaline.error.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

class InvalidTokenExceptionTest {
	
    @Test
    void testDefaultConstructor() {
        InvalidTokenException exception = assertThrows(InvalidTokenException.class, () -> {
            throw new InvalidTokenException();
        });

        assertEquals("El token proporcionado no es válido. Por favor, inicia sesión nuevamente.", exception.getMessage());
    }

    @Test
    void testParameterizedConstructor() {
        String customMessage = "Custom error message";
        InvalidTokenException exception = assertThrows(InvalidTokenException.class, () -> {
            throw new InvalidTokenException(customMessage);
        });

        assertEquals(customMessage, exception.getMessage());
    }

    @Test
    void testResponseStatus() {
        InvalidTokenException exception = new InvalidTokenException();
        
        ResponseStatusException responseStatusException = new ResponseStatusException(HttpStatus.UNAUTHORIZED, exception.getMessage());
        
        assertEquals(HttpStatus.UNAUTHORIZED, responseStatusException.getStatusCode());
        assertEquals("El token proporcionado no es válido. Por favor, inicia sesión nuevamente.", responseStatusException.getReason());
    }

}
