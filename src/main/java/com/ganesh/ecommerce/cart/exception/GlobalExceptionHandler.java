package com.ganesh.ecommerce.cart.exception;

import com.ganesh.ecommerce.cart.dto.ExceptionResponseDTO;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionResponseDTO> handleResourceNotFound(
            ResourceNotFoundException ex,
            WebRequest webRequest) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND, webRequest);
    }

    @ExceptionHandler({
            IllegalArgumentException.class,
            HttpMessageNotReadableException.class,
            ConstraintViolationException.class,
            MethodArgumentTypeMismatchException.class
    })
    public ResponseEntity<ExceptionResponseDTO> handleBadRequest(Exception ex, WebRequest webRequest) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST, webRequest);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponseDTO> handleValidationException(
            MethodArgumentNotValidException ex,
            WebRequest webRequest) {
        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        return buildErrorResponse(message, HttpStatus.BAD_REQUEST, webRequest);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponseDTO> handleGlobalException(Exception ex, WebRequest webRequest) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, webRequest);
    }

    private ResponseEntity<ExceptionResponseDTO> buildErrorResponse(
            String message,
            HttpStatus status,
            WebRequest webRequest) {
        ExceptionResponseDTO exceptionResponseDTO = new ExceptionResponseDTO(
                webRequest.getDescription(false),
                status,
                message,
                LocalDateTime.now());
        return ResponseEntity.status(status).body(exceptionResponseDTO);
    }
}
