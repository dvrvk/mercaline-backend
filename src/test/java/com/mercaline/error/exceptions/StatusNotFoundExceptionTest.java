package com.mercaline.error.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

class StatusNotFoundExceptionTest {

	@Test
	void testStatusNotFoundException() {
		StatusNotFoundException exception = assertThrows(StatusNotFoundException.class, () -> {
			throw new StatusNotFoundException();
		});

		assertEquals("No se puede encontrar el estado seleccionado.", exception.getMessage());
	}

	@Test
	void testResponseStatus() {
		StatusNotFoundException exception = new StatusNotFoundException();

		ResponseStatusException responseStatusException = new ResponseStatusException(HttpStatus.NOT_FOUND,
				exception.getMessage());

		assertEquals(HttpStatus.NOT_FOUND, responseStatusException.getStatusCode());
		assertEquals("No se puede encontrar el estado seleccionado.", responseStatusException.getReason());
	}

}
