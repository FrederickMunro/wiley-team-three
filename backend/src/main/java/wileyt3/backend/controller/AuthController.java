package wileyt3.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wileyt3.backend.config.UserAuthProvider;
import wileyt3.backend.dto.CredentialsDto;
import wileyt3.backend.dto.SignUpDto;
import wileyt3.backend.dto.UserDto;
import wileyt3.backend.service.UserService;

import java.net.URI;
import java.util.Collections;

@RequiredArgsConstructor
@RestController
@CrossOrigin
public class AuthController {

    private final UserService userService;
    private final UserAuthProvider userAuthenticationProvider;

    @PostMapping("/login")
    @Operation(summary = "Authenticate a user", description = "Processes login requests by authenticating users and returning JWT tokens.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Authentication successful"),
            @ApiResponse(responseCode = "400", description = "Invalid login credentials")
    })
    public ResponseEntity<UserDto> login(@RequestBody @Valid CredentialsDto credentialsDto) {
        try {
            UserDto userDto = userService.login(credentialsDto);
            userDto.setToken(userAuthenticationProvider.createToken(userDto.getUsername(), Collections.singletonList(userDto.getRole().getName())));
            return ResponseEntity.ok(userDto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/register")
    @Operation(summary = "Register a new user", description = "Handles user registration and returns the registered user with a JWT token.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "User registered successfully"),
            @ApiResponse(responseCode = "400", description = "Error during registration")
    })
    public ResponseEntity<UserDto> register(@RequestBody @Valid SignUpDto signUpDto) {
        try {
            UserDto createdUser = userService.register(signUpDto);
            createdUser.setToken(userAuthenticationProvider.createToken(createdUser.getUsername(), Collections.singletonList(createdUser.getRole().getName())));
            return ResponseEntity.created(URI.create("/users/" + createdUser.getId())).body(createdUser);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
