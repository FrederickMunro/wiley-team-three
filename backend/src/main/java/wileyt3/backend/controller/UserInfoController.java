package wileyt3.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import wileyt3.backend.dto.DecodedTokenDto;
import wileyt3.backend.util.JwtUtil;

@RestController
public class UserInfoController {
    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/user-info")
    @Operation(summary = "Retrieve user information", description = "Decodes the JWT token to fetch user details such as username, user ID, and roles.")
    @ApiResponse(responseCode = "200", description = "User information retrieved successfully",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = DecodedTokenDto.class)))
    @ApiResponse(responseCode = "400", description = "Bad Request if the token is invalid")
    public ResponseEntity<DecodedTokenDto> getUserInfo(@RequestParam String token) {
        try {
            DecodedTokenDto decodedToken = jwtUtil.decodeJWT(token);
            return ResponseEntity.ok(decodedToken);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
