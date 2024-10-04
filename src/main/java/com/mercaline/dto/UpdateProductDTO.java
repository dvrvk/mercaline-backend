package com.mercaline.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class UpdateProductDTO {
    private Long id;

    private String nombre;

    private String descripcion;

    private BigDecimal precio;

    private String estado;

    private String imagenUrl;

    private String categoria;

}
