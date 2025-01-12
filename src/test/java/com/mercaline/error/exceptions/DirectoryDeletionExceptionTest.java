package com.mercaline.error.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

class DirectoryDeletionExceptionTest {

	@Test
	void testDefaultConstructor() {
		DirectoryDeletionException exception = assertThrows(DirectoryDeletionException.class, () -> {
			throw new DirectoryDeletionException();
		});

		assertEquals(
				"No se pudo eliminar el directorio del usuario. El usuario no se pudo eliminar. Intentalo más tarde.",
				exception.getMessage());
	}

	@Test
	void testParameterizedConstructor() {
		String customMessage = "Custom error message";
		DirectoryDeletionException exception = assertThrows(DirectoryDeletionException.class, () -> {
			throw new DirectoryDeletionException(customMessage);
		});

		assertEquals(customMessage, exception.getMessage());
	}

	@Test
	void testResponseStatus() {
		DirectoryDeletionException exception = new DirectoryDeletionException();

		ResponseStatusException responseStatusException = new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
				exception.getMessage());

		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseStatusException.getStatusCode());
		assertEquals(
				"No se pudo eliminar el directorio del usuario. El usuario no se pudo eliminar. Intentalo más tarde.",
				responseStatusException.getReason());
	}

}
