package com.mercaline.dto;

import com.mercaline.users.dto.GetUserProductDto;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResponseSummaryDTO {

    private Long id;

    private String nombre;

    private String descripcion;

    private String estado;

    private String imagenUrl;

    private BigDecimal precio;

    private String categoria;

    private GetUserProductDto vendedor;
}
