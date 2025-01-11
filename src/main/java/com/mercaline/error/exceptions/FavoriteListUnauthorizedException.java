package com.mercaline.error.exceptions;

/**
 * The Class FavoriteListUnauthorizedException.
 */
public class FavoriteListUnauthorizedException extends RuntimeException {

    /**
     * Instantiates a new favorite list unauthorized exception.
     *
     * @param id the id
     */
    public FavoriteListUnauthorizedException(Long id) {
        super("No tienes permiso para modificar la lista con id: " + id);
    }

    /**
     * Instantiates a new favorite list unauthorized exception.
     */
    public FavoriteListUnauthorizedException() {
        super("Acción no autorizada.");
    }
}
