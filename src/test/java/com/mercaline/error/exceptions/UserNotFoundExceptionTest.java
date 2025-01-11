package com.mercaline.error.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

class UserNotFoundExceptionTest {

	@Test
	void testUserNotFoundException() {
		UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> {
			throw new UserNotFoundException();
		});

		assertEquals("No se puede encontrar el usuario indicado.", exception.getMessage());
	}

	@Test
	void testResponseStatus() {
		UserNotFoundException exception = new UserNotFoundException();

		ResponseStatusException responseStatusException = new ResponseStatusException(HttpStatus.NOT_FOUND,
				exception.getMessage());

		assertEquals(HttpStatus.NOT_FOUND, responseStatusException.getStatusCode());
		assertEquals("No se puede encontrar el usuario indicado.", responseStatusException.getReason());
	}
}
