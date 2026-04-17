package com.ganesh.ecommerce.cart.Controller;

import com.ganesh.ecommerce.cart.dto.UserAddressDTO;
import com.ganesh.ecommerce.cart.entity.AddressType;
import com.ganesh.ecommerce.cart.service.UserAddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/user/address")
@Tag(name = "Addresses", description = "User address management APIs")
public class UserAddressController {

    private UserAddressService userAddressService;

    @Operation(summary = "Add user address", description = "Adds a new address for a user.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Address added"),
            @ApiResponse(responseCode = "400", description = "Invalid address details"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PostMapping("/{userId}")
    public ResponseEntity<UserAddressDTO> addAddress(
            @Parameter(description = "User id", example = "1")
            @PathVariable Long userId,
            @RequestBody @Valid UserAddressDTO userAddressDTO) {
        UserAddressDTO savedAddress = userAddressService.addAddress(userId, userAddressDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAddress);
    }

    @Operation(summary = "Get user addresses", description = "Returns all addresses for a user.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Addresses returned"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<UserAddressDTO>> getUserAddresses(
            @Parameter(description = "User id", example = "1")
            @PathVariable Long userId) {
        return ResponseEntity.ok(userAddressService.getUserAddresses(userId));
    }

    @Operation(summary = "Get address by id", description = "Returns one address by address id.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Address returned"),
            @ApiResponse(responseCode = "404", description = "Address not found")
    })
    @GetMapping("/{addressId}")
    public ResponseEntity<UserAddressDTO> getAddressById(
            @Parameter(description = "Address id", example = "1")
            @PathVariable Long addressId) {
        return ResponseEntity.ok(userAddressService.getAddressById(addressId));
    }

    @Operation(summary = "Update address", description = "Updates an existing user address.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Address updated"),
            @ApiResponse(responseCode = "400", description = "Invalid address details"),
            @ApiResponse(responseCode = "404", description = "Address not found")
    })
    @PutMapping("/{addressId}")
    public ResponseEntity<UserAddressDTO> updateAddress(
            @Parameter(description = "Address id", example = "1")
            @PathVariable Long addressId,
            @RequestBody @Valid UserAddressDTO userAddressDTO) {
        return ResponseEntity.ok(userAddressService.updateAddress(addressId, userAddressDTO));
    }

    @Operation(summary = "Delete address", description = "Deletes an address by address id.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Address deleted"),
            @ApiResponse(responseCode = "404", description = "Address not found")
    })
    @DeleteMapping("/{addressId}")
    public ResponseEntity<Void> deleteAddress(
            @Parameter(description = "Address id", example = "1")
            @PathVariable Long addressId) {
        userAddressService.deleteAddress(addressId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get default address", description = "Returns the default address for a user.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Default address returned"),
            @ApiResponse(responseCode = "404", description = "User or default address not found")
    })
    @GetMapping("/user/{userId}/default")
    public ResponseEntity<UserAddressDTO> getDefaultAddress(
            @Parameter(description = "User id", example = "1")
            @PathVariable Long userId) {
        return ResponseEntity.ok(userAddressService.getDefaultAddress(userId));
    }

    @Operation(summary = "Get addresses by type", description = "Returns user addresses filtered by address type.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Addresses returned"),
            @ApiResponse(responseCode = "400", description = "Invalid address type"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/user/{userId}/type/{addressType}")
    public ResponseEntity<List<UserAddressDTO>> getAddressesByType(
            @Parameter(description = "User id", example = "1")
            @PathVariable Long userId,
            @Parameter(description = "Address type", example = "HOME")
            @PathVariable AddressType addressType) {
        return ResponseEntity.ok(userAddressService.getAddressesByType(userId, addressType));
    }
}

