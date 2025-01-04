package com.mercaline.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import static com.mercaline.config.utils.AppConstants.*;

import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * Gets the name.
 *
 * @return the name
 */
@Getter

/**
 * Sets the name.
 *
 * @param name the new name
 */
@Setter

/**
 * Instantiates a new edits the list fav request.
 */
@NoArgsConstructor

/**
 * Instantiates a new edits the list fav request.
 *
 * @param id the id
 * @param name the name
 */
@AllArgsConstructor

/**
 * To string.
 *
 * @return the java.lang. string
 */
@Builder
public class EditListFavRequest {
    
    /** The id. */
    @NotNull(message = ID_FAV_NOT_NULL_MSG)
    @Min(value = MIN_ID_FAV, message = MIN_ID_FAV_MSG)
    private Long id;

    /** The name. */
    @NotBlank(message = "El nombre de la lista de favoritos es obligatorio")
    @Size(min = 3, message = "El nombre de la lista de favoritos debe tener al menos 3 caracteres")
    private String name;
}
