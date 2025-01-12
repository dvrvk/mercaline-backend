package com.mercaline.error.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

class IllegalOptionExceptionTest {

	@Test
	void testDefaultConstructor() {
		IllegalOptionException exception = assertThrows(IllegalOptionException.class, () -> {
			throw new IllegalOptionException();
		});

		assertEquals(
				"Opción seleccionada para la actualización de las imagenes no válida. Elige una opción válida de las opciones disponibles.",
				exception.getMessage());
	}

	@Test
	void testParameterizedConstructor() {
		String customMessage = "Custom error message";
		IllegalOptionException exception = assertThrows(IllegalOptionException.class, () -> {
			throw new IllegalOptionException(customMessage);
		});

		assertEquals(customMessage, exception.getMessage());
	}

	@Test
	void testResponseStatus() {
		IllegalOptionException exception = new IllegalOptionException();

		ResponseStatusException responseStatusException = new ResponseStatusException(HttpStatus.BAD_REQUEST,
				exception.getMessage());

		assertEquals(HttpStatus.BAD_REQUEST, responseStatusException.getStatusCode());
		assertEquals(
				"Opción seleccionada para la actualización de las imagenes no válida. Elige una opción válida de las opciones disponibles.",
				responseStatusException.getReason());
	}

}
