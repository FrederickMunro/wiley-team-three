package wileyt3.backend.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import wileyt3.backend.config.UserAuthProvider;
import wileyt3.backend.dto.CredentialsDto;
import wileyt3.backend.dto.SignUpDto;
import wileyt3.backend.dto.UserDto;
import wileyt3.backend.service.UserService;

import java.net.URI;

/**
 * Controller that handles authentication and user registration.
 */
@RequiredArgsConstructor
@RestController
public class AuthController {

    private final UserService userService;
    private final UserAuthProvider userAuthenticationProvider;
    /**
     * Processes login requests, authenticating users and returning JWT tokens.
     * @param credentialsDto the credentials of the user attempting to log in
     * @return ResponseEntity containing the authenticated user's details and JWT token
     */
    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody @Valid CredentialsDto credentialsDto) {
        UserDto userDto = userService.login(credentialsDto);
        userDto.setToken(userAuthenticationProvider.createToken(userDto.getLogin()));
        return ResponseEntity.ok(userDto);
    }

    /**
     * Handles user registration and returns the registered user with a JWT token.
     * @param signUpDto DTO containing signup details
     * @return ResponseEntity with the created user details and location header
     */
    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody @Valid SignUpDto signUpDto) {
        UserDto createdUser = userService.register(signUpDto);
        createdUser.setToken(userAuthenticationProvider.createToken(createdUser.getLogin()));
        return ResponseEntity.created(URI.create("/users/" + createdUser.getId())).body(createdUser);
    }
}
