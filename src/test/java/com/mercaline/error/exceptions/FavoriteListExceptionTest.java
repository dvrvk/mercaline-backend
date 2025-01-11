package com.mercaline.error.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

class FavoriteListExceptionTest {

	@Test
	void testFavoriteListException() {
		FavoriteListException exception = assertThrows(FavoriteListException.class, () -> {
			throw new FavoriteListException();
		});

		assertEquals("No puedes tener dos o más listas con el mismo nombre.", exception.getMessage());
	}

	@Test
	void testResponseStatus() {
		FavoriteListException exception = new FavoriteListException();

		ResponseStatusException responseStatusException = new ResponseStatusException(HttpStatus.NOT_FOUND,
				exception.getMessage());

		assertEquals(HttpStatus.NOT_FOUND, responseStatusException.getStatusCode());
		assertEquals("No puedes tener dos o más listas con el mismo nombre.", responseStatusException.getReason());
	}
}
