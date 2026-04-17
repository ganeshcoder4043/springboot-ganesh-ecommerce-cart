package com.ganesh.ecommerce.cart.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Cart item details")
public class UserCartItemDTO {

    @Schema(description = "Cart item id", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "Time when item was added", example = "2026-04-17T15:30:45.123456", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime addedAt;

    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be at least 1")
    @Schema(description = "Product quantity", example = "2")
    private Integer quantity;

    @Valid
    @Schema(description = "Product details. Send product id to use an existing product, or full details to create/reuse by exact match.")
    private ProductDTO product;
}
