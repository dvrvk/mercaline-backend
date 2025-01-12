/**
 * 
 */
package com.mercaline.error.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * @author caromar
 *
 */
class CategoryNotFoundExceptionTest {

	@Test
	void testCategoryNotFoundException() {
		CategoryNotFoundException exception = assertThrows(CategoryNotFoundException.class, () -> {
			throw new CategoryNotFoundException();
		});

		assertEquals("No se puede encontrar la categoría seleccionada.", exception.getMessage());
	}

	@Test
	void testResponseStatus() {
		CategoryNotFoundException exception = new CategoryNotFoundException();

		ResponseStatusException responseStatusException = new ResponseStatusException(HttpStatus.NOT_FOUND,
				exception.getMessage());

		assertEquals(HttpStatus.NOT_FOUND, responseStatusException.getStatusCode());
		assertEquals("No se puede encontrar la categoría seleccionada.", responseStatusException.getReason());
	}

}
