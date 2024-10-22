package com.mercaline.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.validator.constraints.URL;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductRequestDTO {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 100, message = "El tamaño debe estar entre 2 y 100 caracteres")
    private String name;

    @NotBlank(message = "La descripción es obligatoria")
    @Size(min = 3, max = 1000, message = "El tamaño debe estar entre 3 y 1000 caracteres")
    private String description;

    @NotNull(message = "El precio es obligatorio")
    @Digits(integer = 20, fraction = 2, message = "El precio debe ser un número válido con hasta 10 dígitos enteros y 2 decimales")
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor que 0")
    private BigDecimal price;

    @NotNull(message = "El estado es obligatorio")
    private Long status;

    @URL(message = "La URL de la imagen debe ser válida")
    private String urlImage;

    @NotNull(message = "La categoría es obligatoria")
    private Long category;

}
