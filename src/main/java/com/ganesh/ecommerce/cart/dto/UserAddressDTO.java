package com.ganesh.ecommerce.cart.dto;

import com.ganesh.ecommerce.cart.entity.AddressType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "User address details")
public class UserAddressDTO {

    @Schema(description = "Address id", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @NotBlank(message = "Street is required")
    @Schema(description = "Street address", example = "123 Main Street")
    private String street;

    @NotBlank(message = "City is required")
    @Schema(description = "City", example = "Mumbai")
    private String city;

    @NotBlank(message = "State is required")
    @Schema(description = "State", example = "Maharashtra")
    private String state;

    @NotNull(message = "Zip code is required")
    @Positive(message = "Zip code must be positive")
    @Schema(description = "Zip code", example = "400001")
    private Long zip_code;

    @Schema(description = "Whether this is the user's default address", example = "true")
    private Boolean isDefault;

    @NotNull(message = "Address type is required")
    @Schema(description = "Address type", example = "HOME", allowableValues = {"HOME", "OFFICE", "SHIPPING"})
    private AddressType addressType;
}
