package com.mercaline.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Gets the name list.
 *
 * @return the name list
 */
@Getter

/**
 * Sets the name list.
 *
 * @param nameList the new name list
 */
@Setter

/**
 * Instantiates a new favorite lists response DTO.
 */
@NoArgsConstructor

/**
 * Instantiates a new favorite lists response DTO.
 *
 * @param id the id
 * @param nameList the name list
 */
@AllArgsConstructor

/**
 * To string.
 *
 * @return the java.lang. string
 */
@Builder
public class FavoriteProductsInAListResponseDTO {

	/** The id. */
	private Long id;

	/** The name list. */
	private String nameList;
	
	/** The products. */
	private ProductResponseDTO products;

}
