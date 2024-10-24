package com.mercaline.dto;

import com.mercaline.model.CategoryEntity;
import com.mercaline.model.StatusEntity;
import com.mercaline.users.dto.ResponseUserSummaryDTO;
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

    private Long id_status;

    private String imagenUrl;

    private String categoria;

    private Long id_category;

    private ResponseUserSummaryDTO vendedor;
}
