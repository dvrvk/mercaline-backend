package com.mercaline.dto;

import com.mercaline.users.dto.GetUserDto;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResponseDTO {

    private Long id;


    private String nombre;


    private String descripcion;


    private BigDecimal precio;


    private String estado;


    private String imagenUrl;


    private String categoria;

    @NotNull(message = "El camppo vendedor es obligatorio")
    private GetUserDto vendedor;
}
