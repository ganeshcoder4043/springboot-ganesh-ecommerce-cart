package com.ganesh.ecommerce.cart.Controller;

import com.ganesh.ecommerce.cart.dto.UserCartItemDTO;
import com.ganesh.ecommerce.cart.service.UserCartItemService;
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

@RestController
@RequestMapping("/user/usercartitem")
@AllArgsConstructor
@Tag(name = "Cart Items", description = "Cart item management APIs")
public class UserCartItemController {

    private UserCartItemService userCartItemService;

    @Operation(summary = "Add cart item", description = "Adds an item to an existing cart.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Cart item added"),
            @ApiResponse(responseCode = "400", description = "Invalid cart item details"),
            @ApiResponse(responseCode = "404", description = "Cart or product not found")
    })
    @PostMapping({"/user/{userCartId}", "/cart/{userCartId}"})
    public ResponseEntity<UserCartItemDTO> createUserCartItem(
            @Parameter(description = "Cart id", example = "1")
            @PathVariable Long userCartId,
            @RequestBody @Valid UserCartItemDTO userCartItemDTO){
        UserCartItemDTO userCartItem = userCartItemService.createUserCartItem(userCartId, userCartItemDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(userCartItem);
    }

    @Operation(summary = "Get cart item by id", description = "Returns one cart item by item id.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cart item returned"),
            @ApiResponse(responseCode = "404", description = "Cart item not found")
    })
    @GetMapping("/{userCartItemId}")
    public ResponseEntity<UserCartItemDTO> getUserCartItemById(
            @Parameter(description = "Cart item id", example = "1")
            @PathVariable Long userCartItemId) {
        return ResponseEntity.ok(userCartItemService.getUserCartItemById(userCartItemId));
    }

    @Operation(summary = "Get cart items", description = "Returns all items for a cart.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cart items returned"),
            @ApiResponse(responseCode = "404", description = "Cart not found")
    })
    @GetMapping("/cart/{userCartId}")
    public ResponseEntity<List<UserCartItemDTO>> getUserCartItemsByCartId(
            @Parameter(description = "Cart id", example = "1")
            @PathVariable Long userCartId) {
        return ResponseEntity.ok(userCartItemService.getUserCartItemsByCartId(userCartId));
    }

    @Operation(summary = "Update cart item", description = "Updates quantity and optionally product for a cart item.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cart item updated"),
            @ApiResponse(responseCode = "400", description = "Invalid cart item details"),
            @ApiResponse(responseCode = "404", description = "Cart item or product not found")
    })
    @PutMapping("/{userCartItemId}")
    public ResponseEntity<UserCartItemDTO> updateUserCartItem(
            @Parameter(description = "Cart item id", example = "1")
            @PathVariable Long userCartItemId,
            @RequestBody @Valid UserCartItemDTO userCartItemDTO) {
        return ResponseEntity.ok(userCartItemService.updateUserCartItem(userCartItemId, userCartItemDTO));
    }

    @Operation(summary = "Delete cart item", description = "Deletes a cart item by item id.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Cart item deleted"),
            @ApiResponse(responseCode = "404", description = "Cart item not found")
    })
    @DeleteMapping("/{userCartItemId}")
    public ResponseEntity<Void> deleteUserCartItem(
            @Parameter(description = "Cart item id", example = "1")
            @PathVariable Long userCartItemId) {
        userCartItemService.deleteUserCartItem(userCartItemId);
        return ResponseEntity.noContent().build();
    }
}
