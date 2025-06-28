package com.investment.common.exception;

import com.investment.common.exception.response.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.QueryTimeoutException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.sql.SQLException;
import java.sql.SQLTransientConnectionException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Handle PostgreSQL connection failures when Docker container is stopped/disconnected
     */
    @ExceptionHandler(CannotCreateTransactionException.class)
    public ResponseEntity<ErrorResponse> handleCannotCreateTransactionException(
            CannotCreateTransactionException ex, WebRequest request) {

        logger.error("Database connection failed - Cannot create transaction: {}", ex.getMessage(), ex);

        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.SERVICE_UNAVAILABLE.value())
                .error("Database Connection Error")
                .message("Unable to connect to database. Please check if the database service is running.")
                .path(request.getDescription(false).replace("uri=", ""))
                .details(Map.of(
                        "errorType", "DATABASE_CONNECTION_FAILURE",
                        "suggestion", "Verify PostgreSQL container is running and accessible"
                ))
                .build();

        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(errorResponse);
    }

    /**
     * Handle general transaction system exceptions
     */
    @ExceptionHandler(TransactionSystemException.class)
    public ResponseEntity<ErrorResponse> handleTransactionSystemException(
            TransactionSystemException ex, WebRequest request) {

        logger.error("Transaction system error: {}", ex.getMessage(), ex);

        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .error("Transaction System Error")
                .message("A transaction error occurred while processing your request.")
                .path(request.getDescription(false).replace("uri=", ""))
                .details(Map.of(
                        "errorType", "TRANSACTION_SYSTEM_ERROR",
                        "suggestion", "Please try again later or contact support"
                ))
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    /**
     * Handle SQL connection exceptions
     */
    @ExceptionHandler(SQLTransientConnectionException.class)
    public ResponseEntity<ErrorResponse> handleSQLTransientConnectionException(
            SQLTransientConnectionException ex, WebRequest request) {

        logger.error("SQL connection error: {}", ex.getMessage(), ex);

        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.SERVICE_UNAVAILABLE.value())
                .error("Database Connection Error")
                .message("Temporary database connection issue. Please try again.")
                .path(request.getDescription(false).replace("uri=", ""))
                .details(Map.of(
                        "errorType", "SQL_CONNECTION_ERROR",
                        "sqlState", ex.getSQLState() != null ? ex.getSQLState() : "Unknown",
                        "errorCode", String.valueOf(ex.getErrorCode())
                ))
                .build();

        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(errorResponse);
    }

    /**
     * Handle general SQL exceptions
     */
    @ExceptionHandler(SQLException.class)
    public ResponseEntity<ErrorResponse> handleSQLException(SQLException ex, WebRequest request) {

        logger.error("SQL error occurred: {}", ex.getMessage(), ex);

        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .error("Database Error")
                .message("A database error occurred while processing your request.")
                .path(request.getDescription(false).replace("uri=", ""))
                .details(Map.of(
                        "errorType", "SQL_ERROR",
                        "sqlState", ex.getSQLState() != null ? ex.getSQLState() : "Unknown",
                        "errorCode", String.valueOf(ex.getErrorCode())
                ))
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    /**
     * Handle data access resource failure (connection pool exhaustion, etc.)
     */
    @ExceptionHandler(DataAccessResourceFailureException.class)
    public ResponseEntity<ErrorResponse> handleDataAccessResourceFailureException(
            DataAccessResourceFailureException ex, WebRequest request) {

        logger.error("Data access resource failure: {}", ex.getMessage(), ex);

        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.SERVICE_UNAVAILABLE.value())
                .error("Database Resource Error")
                .message("Database resources are currently unavailable. Please try again later.")
                .path(request.getDescription(false).replace("uri=", ""))
                .details(Map.of(
                        "errorType", "DATA_RESOURCE_FAILURE",
                        "suggestion", "Check database connection pool configuration"
                ))
                .build();

        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(errorResponse);
    }

    /**
     * Handle query timeout exceptions
     */
    @ExceptionHandler(QueryTimeoutException.class)
    public ResponseEntity<ErrorResponse> handleQueryTimeoutException(
            QueryTimeoutException ex, WebRequest request) {

        logger.error("Query timeout: {}", ex.getMessage(), ex);

        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.REQUEST_TIMEOUT.value())
                .error("Query Timeout")
                .message("The database query took too long to execute.")
                .path(request.getDescription(false).replace("uri=", ""))
                .details(Map.of(
                        "errorType", "QUERY_TIMEOUT",
                        "suggestion", "Try simplifying your query or contact support"
                ))
                .build();

        return ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT).body(errorResponse);
    }

    /**
     * Handle general data access exceptions
     */
    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ErrorResponse> handleDataAccessException(
            DataAccessException ex, WebRequest request) {

        logger.error("Data access error: {}", ex.getMessage(), ex);

        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .error("Data Access Error")
                .message("An error occurred while accessing the database.")
                .path(request.getDescription(false).replace("uri=", ""))
                .details(Map.of(
                        "errorType", "DATA_ACCESS_ERROR",
                        "rootCause", ex.getRootCause() != null ? ex.getRootCause().getMessage() : "Unknown"
                ))
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    /**
     * Handle any other runtime exceptions
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException ex, WebRequest request) {

        logger.error("Runtime exception: {}", ex.getMessage(), ex);

        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .error("Internal Server Error")
                .message("An unexpected error occurred while processing your request.")
                .path(request.getDescription(false).replace("uri=", ""))
                .details(Map.of(
                        "errorType", "RUNTIME_ERROR",
                        "exceptionClass", ex.getClass().getSimpleName()
                ))
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    /**
     * Handle general exceptions
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex, WebRequest request) {

        logger.error("Unexpected error: {}", ex.getMessage(), ex);

        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .error("Internal Server Error")
                .message("An unexpected error occurred. Please try again later.")
                .path(request.getDescription(false).replace("uri=", ""))
                .details(Map.of(
                        "errorType", "UNEXPECTED_ERROR",
                        "exceptionClass", ex.getClass().getSimpleName()
                ))
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}