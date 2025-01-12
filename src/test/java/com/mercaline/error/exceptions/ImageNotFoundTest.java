package com.mercaline.error.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

class ImageNotFoundTest {

    @Test
    void testImageNotFound() {
        String imageName = "imagen1";
        ImageNotFound exception = assertThrows(ImageNotFound.class, () -> {
            throw new ImageNotFound(imageName);
        });

        assertEquals("Imagen " + imageName + " no encontrada.", exception.getMessage());
    }

    @Test
    void testResponseStatus() {
        String imageName = "imagen1";
        ImageNotFound exception = new ImageNotFound(imageName);
        
        ResponseStatusException responseStatusException = new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage());
        
        assertEquals(HttpStatus.NOT_FOUND, responseStatusException.getStatusCode());
        assertEquals("Imagen " + imageName + " no encontrada.", responseStatusException.getReason());
    }

}
