package com.ganesh.ecommerce.cart.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Schema(description = "Error response")
public class ExceptionResponseDTO {

    @Schema(description = "API path where the error occurred", example = "uri=/user/1")
    private String apiPath;

    @Schema(description = "HTTP status", example = "NOT_FOUND")
    private HttpStatus statusCode;

    @Schema(description = "Error message", example = "User not found with id: 1")
    private String errorMessage;

    @Schema(description = "Error time", example = "2026-04-17T15:30:45.123456")
    private LocalDateTime errorTime;
}
