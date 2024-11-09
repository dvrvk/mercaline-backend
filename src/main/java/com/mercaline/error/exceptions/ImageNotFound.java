package com.mercaline.error.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The Class ImageNotFound.
 *
 * This exception is thrown when an image is not found in the system.
 * The exception is associated with an HTTP 404 (Not Found) status code,
 * indicating that the requested image could not be found in the repository or storage.
 *
 * @author diego
 * @see RuntimeException
 * @see HttpStatus#NOT_FOUND
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ImageNotFound extends RuntimeException {

    /**
     * Instantiates a new ImageNotFound exception with a custom message.
     *
     * @param imagen the name or identifier of the image that was not found
     */
    public ImageNotFound(String imagen) {
        super("Imagen "+ imagen +" no encontrada.");
    }

}
