package com.mercaline.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

/**
 * To string.
 *
 * @return the java.lang. string
 */
@Data

/**
 * Instantiates a new favorite update prod request DTO.
 *
 * @param idList              the id list
 * @param idProduct           the id product
 * @param isDeleteProductList the is delete product list
 * @param isAddProductList    the is add product list
 */
@AllArgsConstructor

/**
 * To string.
 *
 * @return the java.lang. string
 */
@Builder
public class FavoriteUpdateProdRequestDTO {

	/** The id list. */
	@NotNull
	private Long idList;

	/** The id product. */
	@NotNull
	private Long idProduct;

	/** The is delete product list. */
	@NotNull
	private boolean isDeleteProductList;

	/** The is add product list. */
	@NotNull
	private boolean isAddProductList;
}
