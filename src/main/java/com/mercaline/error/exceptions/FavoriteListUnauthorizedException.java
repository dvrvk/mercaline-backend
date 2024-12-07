package com.mercaline.error.exceptions;

public class FavoriteListUnauthorizedException extends RuntimeException {

    public FavoriteListUnauthorizedException(Long id) {
        super("No tienes permiso para modificar la lista con id: " + id);
    }

    public FavoriteListUnauthorizedException() {
        super("Acción no autorizada.");
    }
}
