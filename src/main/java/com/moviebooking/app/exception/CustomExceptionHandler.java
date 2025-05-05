package com.moviebooking.app.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.moviebooking.app.dto.ErrorResponse;

import static com.moviebooking.app.constants.Constants.*;


@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    // Handle validation errors
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));

        ErrorResponse response = new ErrorResponse(
                "Validation Failed",
                status,
                LocalDateTime.now(),
                errors
        );

        return new ResponseEntity<>(response, status);
    }

    // Handle business logic exceptions
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException ex) {
        HttpStatus status = determineHttpStatus(ex.getMessage());

        ErrorResponse response = new ErrorResponse(
                ex.getMessage(),
                status,
                LocalDateTime.now()
        );

        return new ResponseEntity<>(response, status);
    }

    // Fallback for all other exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex) {
        ErrorResponse response = new ErrorResponse(
                "Internal Server Error",
                HttpStatus.INTERNAL_SERVER_ERROR,
                LocalDateTime.now()
        );

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private HttpStatus determineHttpStatus(String message) {
        if (message.contains(USER_NOT_FOUND)) {
            return HttpStatus.NOT_FOUND;
        } else if (message.contains(EMAIL_ALREADY_REGISTERED)) {
            return HttpStatus.CONFLICT;
        } else if (message.contains(INVALID_PASSWORD)) {
            return HttpStatus.UNAUTHORIZED;
        } else if (message.contains(TICKETS_NOT_AVAILABLE)) {
            return HttpStatus.BAD_REQUEST;
        }
        return HttpStatus.BAD_REQUEST;
    }
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
//        Map<String, String> errors = ex.getBindingResult()
//                .getFieldErrors()
//                .stream()
//                .collect(Collectors.toMap(
//                        FieldError::getField,
//                        FieldError::getDefaultMessage,
//                        (existing, replacement) -> existing,
//                        LinkedHashMap::new
//                ));
//        return ResponseEntity.badRequest().body(errors);
//    }
}