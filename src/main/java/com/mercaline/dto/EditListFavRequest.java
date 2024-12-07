package com.mercaline.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import static com.mercaline.config.utils.AppConstants.*;

import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EditListFavRequest {
    @NotNull(message = ID_FAV_NOT_NULL_MSG)
    @Min(value = MIN_ID_FAV, message = MIN_ID_FAV_MSG)
    private Long id;

    @NotBlank(message = "El nombre de la lista de favoritos es obligatorio")
    @Size(min = 3, message = "El nombre de la lista de favoritos debe tener al menos 3 caracteres")
    private String name;
}
