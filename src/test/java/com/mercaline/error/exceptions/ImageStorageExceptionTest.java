package com.mercaline.error.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

class ImageStorageExceptionTest {

    @Test
    void testDefaultConstructor() {
        ImageStorageException exception = assertThrows(ImageStorageException.class, () -> {
            throw new ImageStorageException();
        });

        assertEquals("No se puedo subir la imagen al servidor. Por favor, intenteló más tarde.", exception.getMessage());
    }

    @Test
    void testParameterizedConstructor() {
        String customMessage = "Custom error message";
        ImageStorageException exception = assertThrows(ImageStorageException.class, () -> {
            throw new ImageStorageException(customMessage);
        });

        assertEquals(customMessage, exception.getMessage());
    }

    @Test
    void testResponseStatus() {
        ImageStorageException exception = new ImageStorageException();
        
        ResponseStatusException responseStatusException = new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
        
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseStatusException.getStatusCode());
        assertEquals("No se puedo subir la imagen al servidor. Por favor, intenteló más tarde.", responseStatusException.getReason());
    }

}
