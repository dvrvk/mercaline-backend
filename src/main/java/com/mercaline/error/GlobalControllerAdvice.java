package com.mercaline.error;

import com.mercaline.error.exceptions.*;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The Class GlobalControllerAdvice.
 */
@RestControllerAdvice
public class GlobalControllerAdvice extends ResponseEntityExceptionHandler {

	/**
	 * Handle not found.
	 *
	 * @param ex the ex
	 * @return the response entity
	 */
	// Excepciones de producto
	@ExceptionHandler({ ProductoNotFoundException.class, CategoryNotFoundException.class, StatusNotFoundException.class,
			ImageNotFound.class, FavoriteListException.class, FavoriteListNotFoundException.class })
	public ResponseEntity<ApiError> handleNotFound(Exception ex) {
		ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, ex.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
	}

	/**
	 * Handle not access.
	 *
	 * @param ex the ex
	 * @return the response entity
	 */
	@ExceptionHandler({ ProductUnauthorizedAccessException.class, FavoriteListUnauthorizedException.class })
	public ResponseEntity<ApiError> handleNotAccess(Exception ex) {
		ApiError apiError = new ApiError(HttpStatus.UNAUTHORIZED, ex.getMessage());
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(apiError);
	}

	/**
	 * Handle forbiden.
	 *
	 * @param ex the ex
	 * @return the response entity
	 */
	@ExceptionHandler({ ProductOwnershipException.class })
	public ResponseEntity<ApiError> handleForbiden(Exception ex) {
		ApiError apiError = new ApiError(HttpStatus.FORBIDDEN, ex.getMessage());
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(apiError);
	}

	/**
	 * Handle bad request.
	 *
	 * @param ex the ex
	 * @return the response entity
	 */
	@ExceptionHandler({ PasswordMismatchException.class, IllegalOptionException.class })
	public ResponseEntity<ApiError> handleBadRequest(Exception ex) {
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
	}

	/**
	 * Handle constraint violation exception.
	 *
	 * @param ex the ex
	 * @return the response entity
	 */
	// Excepciones de validacion
	@ExceptionHandler({ ConstraintViolationException.class })
	public ResponseEntity<ApiError> handleConstraintViolationException(ConstraintViolationException ex) {
		String errorMessage = ex.getConstraintViolations().stream().map(ConstraintViolation::getMessage)
				.collect(Collectors.joining(", "));

		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, errorMessage);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
	}

	/**
	 * Handle method argument not valid.
	 *
	 * @param ex      the ex
	 * @param headers the headers
	 * @param status  the status
	 * @param request the request
	 * @return the response entity
	 */
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {

		Map<String, String> errors = getErrorMessagesAsMap(ex.getBindingResult().getAllErrors());

		ApiErrorJSON apiError = new ApiErrorJSON(HttpStatus.BAD_REQUEST, errors);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
	}

	/**
	 * Handle http message not readable.
	 *
	 * @param ex      the ex
	 * @param headers the headers
	 * @param status  the status
	 * @param request the request
	 * @return the response entity
	 */
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {

		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
	}

	/**
	 * Gets the error messages as map.
	 *
	 * @param allErrors the all errors
	 * @return the error messages as map
	 */
	private Map<String, String> getErrorMessagesAsMap(List<ObjectError> allErrors) {
		return allErrors.stream()
				.collect(Collectors.toMap(error -> ((FieldError) error).getField(), ObjectError::getDefaultMessage));
	}

	/**
	 * Handle SQL integrity constraint violation exception.
	 *
	 * @param ex the ex
	 * @return the response entity
	 */
	// Excepciones de base de datos
	@ExceptionHandler(SQLIntegrityConstraintViolationException.class)
	public ResponseEntity<ApiErrorJSON> handleSQLIntegrityConstraintViolationException(
			SQLIntegrityConstraintViolationException ex) {
		System.out.println(ex);
		Map<String, String> message = new HashMap<>();
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		if (ex.getErrorCode() == 1062 && ex.getMessage().contains("email")) {
			message.put("email_duplicate", "El email ya está registrado, introduce otro distinto.");
			status = HttpStatus.CONFLICT;
		} else if (ex.getErrorCode() == 1062 && ex.getMessage().contains("username")) {
			message.put("username_duplicate", "El nombre de usuario ya existe, prueba con otro distinto.");
			status = HttpStatus.CONFLICT;
		} else {
			message.put("internal_server_error", "Error del servidor, intentelo más tarde.");
		}
		ApiErrorJSON apiError = new ApiErrorJSON(status, message);
		return ResponseEntity.status(status).body(apiError);
	}

	/**
	 * Handle database connection exception.
	 *
	 * @param ex the ex
	 * @return the response entity
	 */
	@ExceptionHandler(DatabaseConnectionException.class)
	public ResponseEntity<ApiError> handleDatabaseConnectionException(DatabaseConnectionException ex) {
		ApiError apiError = new ApiError(HttpStatus.SERVICE_UNAVAILABLE, ex.getMessage());
		return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(apiError);
	}

	/**
	 * Handle invalid token exception.
	 *
	 * @param ex the ex
	 * @return the response entity
	 */
	@ExceptionHandler(InvalidTokenException.class)
	public ResponseEntity<ApiError> handleInvalidTokenException(Exception ex) {
		ApiError apiError = new ApiError(HttpStatus.UNAUTHORIZED, ex.getMessage());
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(apiError);
	}

	/**
	 * Handle image storage exception.
	 *
	 * @param ex the ex
	 * @return the response entity
	 */
	@ExceptionHandler(ImageStorageException.class)
	public ResponseEntity<ApiError> handleImageStorageException(ImageStorageException ex) {
		ApiError apiError = new ApiError(HttpStatus.UNAUTHORIZED, ex.getMessage());
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(apiError);
	}

	/**
	 * Handle internal server error.
	 *
	 * @param ex the ex
	 * @return the response entity
	 */
	@ExceptionHandler({ FileDeletionException.class, DirectoryDeletionException.class })
	public ResponseEntity<ApiError> handleInternalServerError(Exception ex) {
		ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiError);
	}

}
