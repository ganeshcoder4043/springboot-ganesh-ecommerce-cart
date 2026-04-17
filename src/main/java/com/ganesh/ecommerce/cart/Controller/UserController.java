package com.ganesh.ecommerce.cart.Controller;

import com.ganesh.ecommerce.cart.dto.UserDTO;
import com.ganesh.ecommerce.cart.service.UserService;
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
@RequestMapping("/user")
@AllArgsConstructor
@Tag(name = "Users", description = "User registration and user management APIs")
public class UserController {

    private UserService userService;

    @Operation(summary = "Register user", description = "Creates a user with optional addresses and an optional initial cart.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "User registered"),
            @ApiResponse(responseCode = "400", description = "Invalid user details")
    })
    @PostMapping("/register")
    public ResponseEntity<UserDTO> createUser(@RequestBody @Valid UserDTO userDTO){
        UserDTO user = userService.createUser(userDTO);
        return  ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @Operation(summary = "Get all users", description = "Returns all registered users.")
    @ApiResponse(responseCode = "200", description = "Users returned")
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @Operation(summary = "Get user by id", description = "Returns one user by user id.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User returned"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUserById(
            @Parameter(description = "User id", example = "1")
            @PathVariable Long userId) {
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    @Operation(summary = "Update user", description = "Updates username, email, and password for an existing user.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User updated"),
            @ApiResponse(responseCode = "400", description = "Invalid user details"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PutMapping("/{userId}")
    public ResponseEntity<UserDTO> updateUser(
            @Parameter(description = "User id", example = "1")
            @PathVariable Long userId,
            @RequestBody @Valid UserDTO userDTO) {
        return ResponseEntity.ok(userService.updateUser(userId, userDTO));
    }

    @Operation(summary = "Delete user", description = "Deletes an existing user by user id.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "User deleted"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(
            @Parameter(description = "User id", example = "1")
            @PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
}
