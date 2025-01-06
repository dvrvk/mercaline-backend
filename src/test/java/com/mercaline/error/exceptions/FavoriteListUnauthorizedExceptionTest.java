package com.mercaline.error.exceptions;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class FavoriteListUnauthorizedExceptionTest {

    @Test
    void testDefaultConstructor() {
        FavoriteListUnauthorizedException exception = assertThrows(FavoriteListUnauthorizedException.class, () -> {
            throw new FavoriteListUnauthorizedException();
        });

        assertEquals("Acción no autorizada.", exception.getMessage());
    }

    @Test
    void testParameterizedConstructor() {
        Long id = 123L;
        FavoriteListUnauthorizedException exception = assertThrows(FavoriteListUnauthorizedException.class, () -> {
            throw new FavoriteListUnauthorizedException(id);
        });

        assertEquals("No tienes permiso para modificar la lista con id: " + id, exception.getMessage());
    }

}
