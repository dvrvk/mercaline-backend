package com.mercaline.error.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

class ProductOwnershipExceptionTest {

	@Test
	void testDefaultConstructor() {
		ProductOwnershipException exception = assertThrows(ProductOwnershipException.class, () -> {
			throw new ProductOwnershipException();
		});

		assertEquals("No tienes permiso para modificar este producto.", exception.getMessage());
	}

	@Test
	void testParameterizedConstructor() {
		String customMessage = "Custom error message";
		ProductOwnershipException exception = assertThrows(ProductOwnershipException.class, () -> {
			throw new ProductOwnershipException(customMessage);
		});

		assertEquals(customMessage, exception.getMessage());
	}

	@Test
	void testResponseStatus() {
		ProductOwnershipException exception = new ProductOwnershipException();

		ResponseStatusException responseStatusException = new ResponseStatusException(HttpStatus.FORBIDDEN,
				exception.getMessage());

		assertEquals(HttpStatus.FORBIDDEN, responseStatusException.getStatusCode());
		assertEquals("No tienes permiso para modificar este producto.", responseStatusException.getReason());
	}

}
