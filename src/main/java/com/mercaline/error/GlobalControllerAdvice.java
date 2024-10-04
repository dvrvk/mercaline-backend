package com.mercaline.error;

import com.mercaline.error.exceptions.ProductUnauthorizedAccessException;
import com.mercaline.error.exceptions.ProductoNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ProductoNotFoundException.class})
    public ResponseEntity<ApiError> handleNotFound(Exception ex) {
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
    }

    @ExceptionHandler({ProductUnauthorizedAccessException.class})
    public ResponseEntity<ApiError> handleNotAccess(Exception ex) {
        ApiError apiError = new ApiError(HttpStatus.UNAUTHORIZED, ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(apiError);
    }
}
