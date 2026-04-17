package com.ganesh.ecommerce.cart.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Product details")
public class ProductDTO {

    @Schema(description = "Product id", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "Product name", example = "iPhone 15 Pro")
    private String name;

    @Schema(description = "Product description", example = "Apple smartphone 256GB")
    private String description;

    @Schema(description = "Product price", example = "129900.0")
    private Double price;
}
