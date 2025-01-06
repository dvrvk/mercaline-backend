package com.mercaline.dto.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.mercaline.dto.ProductResponseDTO;
import com.mercaline.dto.ProductResponseSummaryDTO;
import com.mercaline.model.CategoryEntity;
import com.mercaline.model.ProductEntity;
import com.mercaline.model.StatusEntity;
import com.mercaline.users.Model.UserEntity;
import com.mercaline.users.dto.ResponseUserCompleteDTO;
import com.mercaline.users.dto.ResponseUserProductDTO;

/**
 * The Class ProductoDTOConverterTest.
 */
class ProductoDTOConverterTest {

	/** The producto DTO converter. */
	private ProductoDTOConverter productoDTOConverter;

	/**
	 * Sets the up.
	 */
	@BeforeEach
	void setUp() {
		productoDTOConverter = new ProductoDTOConverter();
	}

	/**
	 * Test convert to product DTO.
	 */
	@Test
	void testConvertToProductDTO() {
		// Configura los datos de prueba
		Long productId = 1L;
		String productName = "Product Name";
		String description = "Product Description";
		Long statusId = 2L;
		String statusName = "Available";
		BigDecimal price = new BigDecimal(20.0);
		String categoryName = "Category";
		Long categoryId = 3L;
		String imageUrl = "http://example.com/image.jpg";
		String cp = "12345";
		boolean sold = false;

		ProductEntity product = new ProductEntity();
		product.setId(productId);
		product.setName(productName);
		product.setDescription(description);
		product.setStatus(new StatusEntity(statusId, statusName));
		product.setPrice(price);
		product.setCategory(new CategoryEntity(categoryId, categoryName));
		product.setUrlImage(imageUrl);
		product.setCp(cp);
		product.setSold(sold);

		UserEntity user = new UserEntity();
		user.setId(4L);
		user.setUsername("username");
		user.setName("name");
		user.setLastname("lastname");
		user.setEmail("email@example.com");
		user.setTel("123456789");
		product.setUser(user);

		// Llama al método a probar
		ProductResponseDTO response = productoDTOConverter.convertToProductDTO(product);

		// Verifica el resultado
		assertNotNull(response);
		assertEquals(productId, response.getId());
		assertEquals(productName, response.getName());
		assertEquals(description, response.getDescription());
		assertEquals(statusId, response.getStatusId());
		assertEquals(statusName, response.getStatus());
		assertEquals(price, response.getPrice());
		assertEquals(categoryName, response.getCategory());
		assertEquals(categoryId, response.getId_category());
		assertEquals(imageUrl, response.getImageUrl());
		assertEquals(cp, response.getCp());
		assertEquals(sold, response.isSold());

		ResponseUserCompleteDTO seller = response.getSeller();
		assertNotNull(seller);
		assertEquals(user.getId(), seller.getId());
		assertEquals(user.getUsername(), seller.getUsername());
		assertEquals(user.getName(), seller.getName());
		assertEquals(user.getLastname(), seller.getLastname());
		assertEquals(user.getEmail(), seller.getEmail());
		assertEquals(user.getTel(), seller.getTel());
	}

	/**
	 * Test convert to get product.
	 */
	@Test
	void testConvertToGetProduct() {
		// Configura los datos de prueba
		Long productId = 1L;
		String productName = "Product Name";
		String description = "Product Description";
		String statusName = "Available";
		BigDecimal price = new BigDecimal(20.0);
		String categoryName = "Category";
		String imageUrl = "http://example.com/image.jpg";
		String cp = "12345";
		boolean sold = false;

		ProductEntity product = new ProductEntity();
		product.setId(productId);
		product.setName(productName);
		product.setDescription(description);
		product.setStatus(new StatusEntity(null, statusName));
		product.setPrice(price);
		product.setCategory(new CategoryEntity(null, categoryName));
		product.setUrlImage(imageUrl);
		product.setCp(cp);
		product.setSold(sold);

		UserEntity user = new UserEntity();
		user.setId(4L);
		user.setUsername("username");

		// Llama al método a probar
		ProductResponseSummaryDTO response = productoDTOConverter.convertToGetProduct(product, user);

		// Verifica el resultado
		assertNotNull(response);
		assertEquals(productId, response.getId());
		assertEquals(productName, response.getName());
		assertEquals(description, response.getDescription());
		assertEquals(statusName, response.getStatus());
		assertEquals(price, response.getPrice());
		assertEquals(categoryName, response.getCategory());
		assertEquals(imageUrl, response.getImageUrl());
		assertEquals(cp, response.getCp());
		assertEquals(sold, response.isSold());

		ResponseUserProductDTO seller = response.getSeller();
		assertNotNull(seller);
		assertEquals(user.getId(), seller.getId());
		assertEquals(user.getUsername(), seller.getUsername());
	}
}
