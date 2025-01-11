package com.mercaline.error.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

class FileDeletionExceptionTest {
	
	@Test
	void testDefaultConstructor() {
		FileDeletionException exception = assertThrows(FileDeletionException.class, () -> {
			throw new FileDeletionException();
		});

		assertEquals(
				"No se pudo eliminar el archivo del usuario. No se pudo eliminar el usuario. Intentaló más tarde. ",
				exception.getMessage());
	}

	@Test
	void testParameterizedConstructor() {
		String customMessage = "Custom error message";
		FileDeletionException exception = assertThrows(FileDeletionException.class, () -> {
			throw new FileDeletionException(customMessage);
		});

		assertEquals(customMessage, exception.getMessage());
	}

	@Test
	void testResponseStatus() {
		FileDeletionException exception = new FileDeletionException();

		ResponseStatusException responseStatusException = new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
				exception.getMessage());

		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseStatusException.getStatusCode());
		assertEquals(
				"No se pudo eliminar el archivo del usuario. No se pudo eliminar el usuario. Intentaló más tarde. ",
				responseStatusException.getReason());
	}

}
