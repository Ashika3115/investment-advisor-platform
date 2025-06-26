package com.investment.common.exception;

import com.investment.common.apiresponse.ApiResponse;
import jakarta.persistence.EntityNotFoundException;
import org.postgresql.util.PSQLException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.*;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleEntityNotFound(EntityNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.<Void>builder()
                        .status("error")
                        .message(ex.getMessage())
                        .data(null)
                        .build());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Void>> handleBadRequest(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.<Void>builder()
                        .status("error")
                        .message(ex.getMessage())
                        .data(null)
                        .build());
    }

    @ExceptionHandler(CannotGetJdbcConnectionException.class)
    public ApiResponse<?> handleDatabaseConnectionError(CannotGetJdbcConnectionException ex) {
        return ApiResponse.builder()
                .status("error")
                .message("Database connection failed. Please try again later.")
                .data(null)
                .build();
    }

    @ExceptionHandler(DataAccessException.class)
    public ApiResponse<?> handleDataAccessException(DataAccessException ex) {
        return ApiResponse.builder()
                .status("error")
                .message("Database error occurred.")
                .data(null)
                .build();
    }


    @ExceptionHandler(CannotCreateTransactionException.class)
    public ResponseEntity<ApiResponse<?>> handleCannotCreateTransactionException(CannotCreateTransactionException ex) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(
                ApiResponse.builder()
                        .status("error")
                        .message("Cannot connect to the database. Please try again later.")
                        .data(null)
                        .build()
        );
    }

    @ExceptionHandler(PSQLException.class)
    public ResponseEntity<ApiResponse<?>> handleCannotCreateTransactionException(PSQLException ex) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(
                ApiResponse.builder()
                        .status("error")
                        .message("The connection attempt failed. Please try again later.")
                        .data(null)
                        .build()
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleInternal(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.<Void>builder()
                        .status("error")
                        .message("Unexpected error occurred: " + ex.getMessage())
                        .data(null)
                        .build());
    }
}
