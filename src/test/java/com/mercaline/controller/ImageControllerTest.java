package com.mercaline.controller;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.nio.file.Files;
import java.util.Base64;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.mercaline.dto.ApiResponse;
import com.mercaline.error.exceptions.ProductoNotFoundException;
import com.mercaline.model.ProductEntity;
import com.mercaline.service.ProductService;
import com.mercaline.utils.ImagePathUtil;

/**
 * The Class ImageControllerTest.
 */
class ImageControllerTest {

	/** The product service. */
	@Mock
	private ProductService productService;

	/** The image controller. */
	@InjectMocks
	private ImageController imageController;

	/**
	 * Sets the up.
	 */
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	/**
	 * Test find main image found.
	 *
	 * @throws Exception the exception
	 */
	@Test
	void testFindMain_ImageFound() throws Exception {
		// Configura los datos de prueba
		Long productId = 1L;
		String imagePath = "imagen1.jpg";

		ProductEntity product = new ProductEntity();
		product.setUrlImage(ImagePathUtil.getImageBytes(imagePath).toString());

		when(productService.findById(productId)).thenReturn(Optional.of(product));

		byte[] imageBytes = Files.readAllBytes(ImagePathUtil.getImageBytes(imagePath));

		ResponseEntity<?> response = imageController.findMain(productId);

		// Verifica el resultado
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());

		Object responseBody = response.getBody();
		assertNotNull(responseBody);
		assertTrue(responseBody instanceof ByteArrayResource);

		ByteArrayResource resource = (ByteArrayResource) responseBody;
		assertArrayEquals(imageBytes, resource.getByteArray());

		HttpHeaders headers = response.getHeaders();
		assertEquals(MediaType.IMAGE_JPEG, headers.getContentType());
		assertEquals(imageBytes.length, headers.getContentLength());
	}

	/**
	 * Test find main image not found.
	 */
	@Test
	void testFindMain_ImageNotFound() {
		// Configura los datos de prueba
		Long productId = 1L;

		when(productService.findById(productId)).thenReturn(Optional.empty());

		// Verifica la excepción
		assertThrows(ProductoNotFoundException.class, () -> {
			imageController.findMain(productId);
		});
	}

	/**
	 * Test find all images found.
	 *
	 * @throws Exception the exception
	 */
	@Test
	void testFindAll_ImagesFound() throws Exception {
		// Configura los datos de prueba
		Long productId = 1L;
		String imagePath1 = "imagen1.jpg";
		String imagePath2 = "imagen2.jpg";

		ProductEntity product = new ProductEntity();
		product.setUrlImage(ImagePathUtil.getImageBytes(imagePath1).toString() + ";" + ImagePathUtil.getImageBytes(imagePath2).toString());

		when(productService.findById(productId)).thenReturn(Optional.of(product));

		byte[] imageBytes1 = Files.readAllBytes(ImagePathUtil.getImageBytes(imagePath1));
		byte[] imageBytes2 = Files.readAllBytes(ImagePathUtil.getImageBytes(imagePath2));
		String base64Image1 = Base64.getEncoder().encodeToString(imageBytes1);
		String base64Image2 = Base64.getEncoder().encodeToString(imageBytes2);
		String imagesString = base64Image1 + "," + base64Image2;

		ResponseEntity<?> response = imageController.findAll(productId);

		// Verifica el resultado
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());

		Object responseBody = response.getBody();
		assertNotNull(responseBody);
		assertTrue(responseBody instanceof ApiResponse);

		ApiResponse apiResponse = (ApiResponse) responseBody;
		assertEquals(imagesString, apiResponse.getMensaje());
	}

	/**
	 * Test find all image not found.
	 */
	@Test
	void testFindAll_ImageNotFound() {
		// Configura los datos de prueba
		Long productId = 1L;

		when(productService.findById(productId)).thenReturn(Optional.empty());

		// Verifica la excepción
		assertThrows(ProductoNotFoundException.class, () -> {
			imageController.findAll(productId);
		});
	}
}
