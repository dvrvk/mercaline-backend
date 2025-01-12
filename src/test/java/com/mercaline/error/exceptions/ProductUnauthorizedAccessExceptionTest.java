package com.mercaline.error.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class ProductUnauthorizedAccessExceptionTest {

	@Test
	void testDefaultConstructor() {
		ProductUnauthorizedAccessException exception = assertThrows(ProductUnauthorizedAccessException.class, () -> {
			throw new ProductUnauthorizedAccessException();
		});

		assertEquals("Acción no autorizada.", exception.getMessage());
	}

	@Test
	void testParameterizedConstructor() {
		Long id = 123L;
		ProductUnauthorizedAccessException exception = assertThrows(ProductUnauthorizedAccessException.class, () -> {
			throw new ProductUnauthorizedAccessException(id);
		});

		assertEquals("No tienes permiso para modificar el producto con id: " + id, exception.getMessage());
	}

}
