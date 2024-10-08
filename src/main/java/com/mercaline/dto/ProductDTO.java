package com.mercaline.dto;

import com.mercaline.users.dto.GetUserDto;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDTO {

    private Long id;

    private String nombre;

    private String descripcion;

    private BigDecimal precio;

    private String estado;

    private String imagenUrl;

    private String categoria;

    private GetUserDto vendedor;
}
