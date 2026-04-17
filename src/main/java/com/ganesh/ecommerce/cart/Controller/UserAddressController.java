package com.ganesh.ecommerce.cart.Controller;

import com.ganesh.ecommerce.cart.dto.UserAddressDTO;
import com.ganesh.ecommerce.cart.service.UserAddressService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/user/address")
public class UserAddressController {

    private UserAddressService userAddressService;

    // Add new address to user
    @PostMapping("/{userId}")
    public ResponseEntity<UserAddressDTO> addAddress(
            @PathVariable Long userId,
            @RequestBody @Valid UserAddressDTO userAddressDTO) {
        try {
            UserAddressDTO savedAddress = userAddressService.addAddress(userId, userAddressDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedAddress);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}

    // Get all addresses for a user
   /* @GetMapping("/user/{userId}")
    public ResponseEntity<List<UserAddressDTO>> getUserAddresses(@PathVariable Long userId) {
        try {
            List<UserAddressDTO> addresses = userAddressService.getUserAddresses(userId);
            return ResponseEntity.ok(addresses);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Get specific address by ID
    @GetMapping("/{addressId}")
    public ResponseEntity<UserAddressDTO> getAddressById(@PathVariable Long addressId) {
        try {
            UserAddressDTO address = userAddressService.getAddressById(addressId);
            return ResponseEntity.ok(address);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Update address
    @PutMapping("/{addressId}")
    public ResponseEntity<UserAddressDTO> updateAddress(
            @PathVariable Long addressId,
            @RequestBody @Valid UserAddressDTO userAddressDTO) {
        try {
            UserAddressDTO updatedAddress = userAddressService.updateAddress(addressId, userAddressDTO);
            return ResponseEntity.ok(updatedAddress);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // Delete address
    @DeleteMapping("/{addressId}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long addressId) {
        try {
            userAddressService.deleteAddress(addressId);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Get default address for user
    @GetMapping("/user/{userId}/default")
    public ResponseEntity<UserAddressDTO> getDefaultAddress(@PathVariable Long userId) {
        try {
            UserAddressDTO defaultAddress = userAddressService.getDefaultAddress(userId);
            if (defaultAddress != null) {
                return ResponseEntity.ok(defaultAddress);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Get addresses by type for user
    @GetMapping("/user/{userId}/type/{addressType}")
    public ResponseEntity<List<UserAddressDTO>> getAddressesByType(
            @PathVariable Long userId,
            @PathVariable String addressType) {
        try {
            List<UserAddressDTO> addresses = userAddressService.getAddressesByType(userId, addressType);
            return ResponseEntity.ok(addresses);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }*/

