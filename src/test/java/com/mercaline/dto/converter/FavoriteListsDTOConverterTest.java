package com.mercaline.dto.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.mercaline.dto.FavoriteListsResponseDTO;
import com.mercaline.dto.FavoriteProductsInAListResponseDTO;
import com.mercaline.dto.ProductResponseDTO;
import com.mercaline.model.CategoryEntity;
import com.mercaline.model.ListFavoriteEntity;
import com.mercaline.model.ProductEntity;
import com.mercaline.model.StatusEntity;

/**
 * The Class FavoriteListsDTOConverterTest.
 */
class FavoriteListsDTOConverterTest {

	/** The favorite lists DTO converter. */
	private FavoriteListsDTOConverter favoriteListsDTOConverter;

	/**
	 * Sets the up.
	 */
	@BeforeEach
	void setUp() {
		favoriteListsDTOConverter = new FavoriteListsDTOConverter();
	}

	/**
	 * Test convert to response favorite lists summary DTO.
	 */
	@Test
	void testConvertToResponseFavoriteListsSummaryDTO() {
		// Configura los datos de prueba
		Long id = 1L;
		String nameList = "Favorite List";
		Integer productSize = 10;

		// Llama al método a probar
		FavoriteListsResponseDTO response = favoriteListsDTOConverter.convertToResponseFavoriteListsSummaryDTO(id,
				nameList, productSize);

		// Verifica el resultado
		assertNotNull(response);
		assertEquals(id, response.getId());
		assertEquals(nameList, response.getNameList());
		assertEquals(productSize, response.getProductSize());
	}

	/**
	 * Test convert to response favorite products summary DTO.
	 */
	@Test
	void testConvertToResponseFavoriteProductsSummaryDTO() {
		// Configura los datos de prueba
		Long favoriteListId = 1L;
		String favoriteListName = "Favorite List";
		Long productId = 2L;
		String productName = "Product";
		BigDecimal productPrice = new BigDecimal(20.0);
		String productCategory = "Category";
		String productStatus = "Available";
		String productImageUrl = "http://example.com/image.jpg";
		boolean productSold = false;

		ListFavoriteEntity favoriteList = new ListFavoriteEntity();
		favoriteList.setId(favoriteListId);
		favoriteList.setName(favoriteListName);

		ProductEntity product = new ProductEntity();
		product.setId(productId);
		product.setName(productName);
		product.setPrice(productPrice);
		product.setCategory(new CategoryEntity(1L, productCategory));
		product.setStatus(new StatusEntity(1L, productStatus));
		product.setUrlImage(productImageUrl);
		product.setSold(productSold);

		// Llama al método a probar
		FavoriteProductsInAListResponseDTO response = favoriteListsDTOConverter
				.convertToResponseFavoriteProductsSummaryDTO(favoriteList, product);

		// Verifica el resultado
		assertNotNull(response);
		assertEquals(favoriteListId, response.getId());
		assertEquals(favoriteListName, response.getNameList());

		ProductResponseDTO productResponse = response.getProducts();
		assertNotNull(productResponse);
		assertEquals(productId, productResponse.getId());
		assertEquals(productName, productResponse.getName());
		assertEquals(productPrice, productResponse.getPrice());
		assertEquals(productCategory, productResponse.getCategory());
		assertEquals(productStatus, productResponse.getStatus());
		assertEquals(productImageUrl, productResponse.getImageUrl());
		assertEquals(productSold, productResponse.isSold());
	}
}
