package com.ganesh.ecommerce.cart.Controller;

import com.ganesh.ecommerce.cart.dto.UserCartDTO;
import com.ganesh.ecommerce.cart.service.UserCartService;
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

@RestController
@AllArgsConstructor
@RequestMapping("/user/usercart")
@Tag(name = "Carts", description = "User cart management APIs")
public class UserCartController {

    private UserCartService userCartService;

    @Operation(summary = "Create user cart", description = "Creates a cart for a user. A user can have only one cart.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Cart created"),
            @ApiResponse(responseCode = "400", description = "Invalid cart details or cart already exists"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PostMapping("/{userId}")
    public ResponseEntity<UserCartDTO> createUserCart(
            @Parameter(description = "User id", example = "1")
            @PathVariable Long userId,
            @RequestBody @Valid UserCartDTO userCartDTO){
        UserCartDTO userCart = userCartService.createUserCart(userId, userCartDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(userCart);
    }

    @Operation(summary = "Get cart by id", description = "Returns one cart by cart id.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cart returned"),
            @ApiResponse(responseCode = "404", description = "Cart not found")
    })
    @GetMapping("/{userCartId}")
    public ResponseEntity<UserCartDTO> getUserCartById(
            @Parameter(description = "Cart id", example = "1")
            @PathVariable Long userCartId) {
        return ResponseEntity.ok(userCartService.getUserCartById(userCartId));
    }

    @Operation(summary = "Get cart by user id", description = "Returns a user's cart by user id.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cart returned"),
            @ApiResponse(responseCode = "404", description = "User or cart not found")
    })
    @GetMapping("/user/{userId}")
    public ResponseEntity<UserCartDTO> getUserCartByUserId(
            @Parameter(description = "User id", example = "1")
            @PathVariable Long userId) {
        return ResponseEntity.ok(userCartService.getUserCartByUserId(userId));
    }

    @Operation(summary = "Replace cart items", description = "Replaces all items in an existing cart.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cart updated"),
            @ApiResponse(responseCode = "400", description = "Invalid cart details"),
            @ApiResponse(responseCode = "404", description = "Cart or product not found")
    })
    @PutMapping("/{userCartId}")
    public ResponseEntity<UserCartDTO> updateUserCart(
            @Parameter(description = "Cart id", example = "1")
            @PathVariable Long userCartId,
            @RequestBody @Valid UserCartDTO userCartDTO) {
        return ResponseEntity.ok(userCartService.updateUserCart(userCartId, userCartDTO));
    }

    @Operation(summary = "Clear cart items", description = "Removes all items from a cart.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cart cleared"),
            @ApiResponse(responseCode = "404", description = "Cart not found")
    })
    @DeleteMapping("/{userCartId}/items")
    public ResponseEntity<UserCartDTO> clearUserCart(
            @Parameter(description = "Cart id", example = "1")
            @PathVariable Long userCartId) {
        return ResponseEntity.ok(userCartService.clearUserCart(userCartId));
    }

    @Operation(summary = "Delete cart", description = "Deletes a cart by cart id.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Cart deleted"),
            @ApiResponse(responseCode = "404", description = "Cart not found")
    })
    @DeleteMapping("/{userCartId}")
    public ResponseEntity<Void> deleteUserCart(
            @Parameter(description = "Cart id", example = "1")
            @PathVariable Long userCartId) {
        userCartService.deleteUserCart(userCartId);
        return ResponseEntity.noContent().build();
    }
}
