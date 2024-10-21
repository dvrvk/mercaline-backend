package com.mercaline.error;

import com.mercaline.error.exceptions.*;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.SQLTransientConnectionException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalControllerAdvice extends ResponseEntityExceptionHandler {

    // Excepciones de producto
    @ExceptionHandler({ProductoNotFoundException.class, CategoryNotFoundException.class, StatusNotFoundException.class})
    public ResponseEntity<ApiError> handleNotFound(Exception ex) {
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
    }


    @ExceptionHandler({ProductUnauthorizedAccessException.class})
    public ResponseEntity<ApiError> handleNotAccess(Exception ex) {
        ApiError apiError = new ApiError(HttpStatus.UNAUTHORIZED, ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(apiError);
    }

    // Excepciones de validacion
    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<ApiError> handleConstraintViolationException(ConstraintViolationException ex) {
        String errorMessage = ex.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(", "));

        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, errorMessage);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        Map<String, String> errors = getErrorMessagesAsMap(ex.getBindingResult().getAllErrors());


        ApiErrorJSON apiError = new ApiErrorJSON(HttpStatus.BAD_REQUEST, errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

    private Map<String, String> getErrorMessagesAsMap(List<ObjectError> allErrors) {
        return allErrors.stream()
                .collect(Collectors.toMap(
                        error -> ((FieldError) error).getField(),
                        ObjectError::getDefaultMessage
                ));
    }

    // Excepciones de base de datos
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<ApiErrorJSON> handleSQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException ex) {
        System.out.println(ex.getErrorCode());
        Map<String, String> message = new HashMap<>();
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        if(ex.getErrorCode() == 1062 && ex.getMessage().contains("unique_email")) {
            message.put("email_duplicate", "El email ya está registrado, introduce otro distinto.");
            status = HttpStatus.CONFLICT;
        } else if(ex.getErrorCode() == 1062) {
            message.put("username_duplicate", "El nombre de usuario ya existe, prueba con otro distinto.");
            status = HttpStatus.CONFLICT;
        } else {
            message.put("internal_server_error","Error del servidor, intentelo más tarde.");
        }
        ApiErrorJSON apiError = new ApiErrorJSON(status, message);
        return ResponseEntity.status(status).body(apiError);
    }

    @ExceptionHandler(DatabaseConnectionException.class)
    public ResponseEntity<ApiError> handleDatabaseConnectionException(DatabaseConnectionException ex) {
        ApiError apiError = new ApiError(HttpStatus.SERVICE_UNAVAILABLE, ex.getMessage());
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(apiError);
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<ApiError> handleInvalidTokenException(Exception ex) {
        ApiError apiError = new ApiError(HttpStatus.UNAUTHORIZED, ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(apiError);
    }



}
