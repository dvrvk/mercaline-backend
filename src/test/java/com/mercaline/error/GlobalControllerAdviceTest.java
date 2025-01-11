package com.mercaline.error;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Collections;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.mercaline.error.exceptions.DatabaseConnectionException;
import com.mercaline.error.exceptions.FileDeletionException;
import com.mercaline.error.exceptions.ImageStorageException;
import com.mercaline.error.exceptions.InvalidTokenException;
import com.mercaline.error.exceptions.PasswordMismatchException;
import com.mercaline.error.exceptions.ProductOwnershipException;
import com.mercaline.error.exceptions.ProductUnauthorizedAccessException;
import com.mercaline.error.exceptions.ProductoNotFoundException;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

class GlobalControllerAdviceTest {

	@Test
	void testHandleNotFound() {
		ProductoNotFoundException ex = new ProductoNotFoundException();
		ResponseEntity<ApiError> response = new GlobalControllerAdvice().handleNotFound(ex);
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		assertEquals("No se pueden encontrar productos", response.getBody().getMensaje());
	}

	@Test
	void testHandleNotAccess() {
		ProductUnauthorizedAccessException ex = new ProductUnauthorizedAccessException();
		ResponseEntity<ApiError> response = new GlobalControllerAdvice().handleNotAccess(ex);
		assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
		assertEquals("Acción no autorizada.", response.getBody().getMensaje());
	}

	@Test
	void testHandleForbiden() {
		ProductOwnershipException ex = new ProductOwnershipException();
		ResponseEntity<ApiError> response = new GlobalControllerAdvice().handleForbiden(ex);
		assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
		assertEquals("No tienes permiso para modificar este producto.", response.getBody().getMensaje());
	}

	@Test
	void testHandleBadRequest() {
		PasswordMismatchException ex = new PasswordMismatchException();
		ResponseEntity<ApiError> response = new GlobalControllerAdvice().handleBadRequest(ex);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertEquals("La contraseña no es correcta. Vuelve a intentarlo.", response.getBody().getMensaje());
	}

	@Test
	void testHandleConstraintViolationException() {
		Set<ConstraintViolation<Object>> violations = Collections.emptySet();
		ConstraintViolationException ex = new ConstraintViolationException(violations);
		ResponseEntity<ApiError> response = new GlobalControllerAdvice().handleConstraintViolationException(ex);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}

	@Test
	void testHandleMethodArgumentNotValid() {
		BindingResult bindingResult = new BindException(new Object(), "objectName");
		bindingResult.addError(new FieldError("objectName", "field", "defaultMessage"));
		MethodArgumentNotValidException ex = new MethodArgumentNotValidException(null, bindingResult);
		ResponseEntity<Object> response = new GlobalControllerAdvice().handleMethodArgumentNotValid(ex, null, null,
				null);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}

	@Test
	void testHandleHttpMessageNotReadable() {
		@SuppressWarnings("deprecation")
		HttpMessageNotReadableException ex = new HttpMessageNotReadableException("Mensaje no legible");
		ResponseEntity<Object> response = new GlobalControllerAdvice().handleHttpMessageNotReadable(ex, null, null,
				null);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertEquals("Mensaje no legible", ((ApiError) response.getBody()).getMensaje());
	}

	@Test
	void testHandleSQLIntegrityConstraintViolationException() {
		SQLIntegrityConstraintViolationException ex = new SQLIntegrityConstraintViolationException();
		ResponseEntity<ApiErrorJSON> response = new GlobalControllerAdvice()
				.handleSQLIntegrityConstraintViolationException(ex);
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}

	@Test
	void testHandleDatabaseConnectionException() {
		DatabaseConnectionException ex = new DatabaseConnectionException();
		ResponseEntity<ApiError> response = new GlobalControllerAdvice().handleDatabaseConnectionException(ex);
		assertEquals(HttpStatus.SERVICE_UNAVAILABLE, response.getStatusCode());
		assertEquals("No se puede conectar a la base de datos, intentelo más tarde.", response.getBody().getMensaje());
	}

	@Test
	void testHandleInvalidTokenException() {
		InvalidTokenException ex = new InvalidTokenException();
		ResponseEntity<ApiError> response = new GlobalControllerAdvice().handleInvalidTokenException(ex);
		assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
		assertEquals("El token proporcionado no es válido. Por favor, inicia sesión nuevamente.",
				response.getBody().getMensaje());
	}

	@Test
	void testHandleImageStorageException() {
		ImageStorageException ex = new ImageStorageException();
		ResponseEntity<ApiError> response = new GlobalControllerAdvice().handleImageStorageException(ex);
		assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
		assertEquals("No se puedo subir la imagen al servidor. Por favor, intenteló más tarde.",
				response.getBody().getMensaje());
	}

	@Test
	void testHandleInternalServerError() {
		FileDeletionException ex = new FileDeletionException();
		ResponseEntity<ApiError> response = new GlobalControllerAdvice().handleInternalServerError(ex);
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
		assertEquals(
				"No se pudo eliminar el archivo del usuario. No se pudo eliminar el usuario. Intentaló más tarde. ",
				response.getBody().getMensaje());
	}
}
