package com.mercaline.error.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

class DatabaseConnectionExceptionTest {

	@Test
	void testDatabaseConnectionException() {
		DatabaseConnectionException exception = assertThrows(DatabaseConnectionException.class, () -> {
			throw new DatabaseConnectionException();
		});

		assertEquals("No se puede conectar a la base de datos, intentelo más tarde.", exception.getMessage());
	}

	@Test
	void testResponseStatus() {
		DatabaseConnectionException exception = new DatabaseConnectionException();

		ResponseStatusException responseStatusException = new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,
				exception.getMessage());

		assertEquals(HttpStatus.SERVICE_UNAVAILABLE, responseStatusException.getStatusCode());
		assertEquals("No se puede conectar a la base de datos, intentelo más tarde.",
				responseStatusException.getReason());
	}

}
