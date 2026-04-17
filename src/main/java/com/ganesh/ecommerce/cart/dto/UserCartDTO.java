package com.ganesh.ecommerce.cart.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "User cart details")
public class UserCartDTO {

    @Schema(description = "Cart id", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "Cart creation time", example = "2026-04-17T15:30:45.123456", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime createdAt;

    @Schema(description = "Cart last update time", example = "2026-04-17T15:30:45.123456", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime updatedAt;

    @Valid
    @Schema(description = "Items in the cart")
    private List<UserCartItemDTO> userCartItems;
}
