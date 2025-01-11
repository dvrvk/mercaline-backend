package com.mercaline.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

/**
 * Sets the mensaje.
 *
 * @param mensaje the new mensaje
 */
@Setter

/**
 * Gets the mensaje.
 *
 * @return the mensaje
 */
@Getter

/**
 * Instantiates a new api error.
 *
 * @param status the status
 * @param mensaje the mensaje
 */
@RequiredArgsConstructor

/**
 * Instantiates a new api error.
 */
@NoArgsConstructor
public class ApiError {

    /** The status. */
    @NonNull
    private HttpStatus status;
    
    /** The fecha. */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy hh:mm:ss")
    private LocalDateTime fecha = LocalDateTime.now();
    
    /** The mensaje. */
    @NonNull
    private String mensaje;

}
