package com.ganesh.ecommerce.cart.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "User details")
public class UserDTO {

    @Schema(description = "User id", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @NotBlank(message = "Username is required")
    @Schema(description = "Username", example = "ganesh_123")
    private String username;

    @NotBlank(message = "Password is required")
    @Schema(description = "User password", example = "securePass@123")
    private String password;

    @Email(message = "Email must be valid")
    @NotBlank(message = "Email is required")
    @Schema(description = "User email", example = "ganesh@example.com")
    private String email;

    @Valid
    @Schema(description = "User addresses")
    private List<UserAddressDTO> userAddress;

    @Valid
    @Schema(description = "User cart")
    private UserCartDTO userCart;
}
