package com.mercaline.dto;

import java.math.BigDecimal;

import com.mercaline.users.dto.ResponseUserSummaryDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Gets the vendedor.
 *
 * @return the vendedor
 */
@Getter

/**
 * Sets the vendedor.
 *
 * @param vendedor the new vendedor
 */
@Setter

/**
 * Instantiates a new product response DTO.
 */
@NoArgsConstructor

/**
 * Instantiates a new product response DTO.
 *
 * @param id the id
 * @param nombre the nombre
 * @param descripcion the descripcion
 * @param precio the precio
 * @param estado the estado
 * @param id_status the id status
 * @param imagenUrl the imagen url
 * @param categoria the categoria
 * @param id_category the id category
 * @param vendedor the vendedor
 */
@AllArgsConstructor

/**
 * To string.
 *
 * @return the java.lang. string
 */
@Builder
public class ProductResponseDTO {

    /** The id. */
    private Long id;

    /** The nombre. */
    private String nombre;

    /** The descripcion. */
    private String descripcion;

    /** The precio. */
    private BigDecimal precio;

    /** The estado. */
    private String estado;

    /** The id status. */
    private Long id_status;

    /** The imagen url. */
    private String imagenUrl;

    /** The categoria. */
    private String categoria;

    /** The id category. */
    private Long id_category;

    /** The vendedor. */
    private ResponseUserSummaryDTO vendedor;
}
