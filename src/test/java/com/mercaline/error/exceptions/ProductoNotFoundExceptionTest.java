package com.mercaline.error.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

class ProductoNotFoundExceptionTest {

    @Test
    void testDefaultConstructor() {
        ProductoNotFoundException exception = assertThrows(ProductoNotFoundException.class, () -> {
            throw new ProductoNotFoundException();
        });

        assertEquals("No se pueden encontrar productos", exception.getMessage());
    }

    @Test
    void testParameterizedConstructor() {
        Long id = 123L;
        ProductoNotFoundException exception = assertThrows(ProductoNotFoundException.class, () -> {
            throw new ProductoNotFoundException(id);
        });

        assertEquals("No se puede encontrar el producto con el ID: " + id, exception.getMessage());
    }

    @Test
    void testResponseStatus() {
        ProductoNotFoundException exception = new ProductoNotFoundException();
        
        ResponseStatusException responseStatusException = new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage());
        
        assertEquals(HttpStatus.NOT_FOUND, responseStatusException.getStatusCode());
        assertEquals("No se pueden encontrar productos", responseStatusException.getReason());
    }

}
