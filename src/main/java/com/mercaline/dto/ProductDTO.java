package com.mercaline.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDTO {

    private String nombre;

    private String descripcion;

    private String estado;

    private String imagen;

    private BigDecimal precio;

    private String categoria;
}
