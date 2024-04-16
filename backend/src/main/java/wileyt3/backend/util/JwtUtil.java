package wileyt3.backend.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import wileyt3.backend.dto.DecodedTokenDto;
import wileyt3.backend.dto.UserDto;
import wileyt3.backend.service.UserService;

import java.util.Collections;
import java.util.List;

@Component

public class JwtUtil {

    @Value("${security.jwt.token.secret-key}")
    private String secretKey;

    @Autowired
    private final UserService userService;

    public JwtUtil(UserService userService) {
        this.userService = userService;
    }

    /**
     * Decodes a JWT token and extracts user information.
     *
     * @param token JWT token
     * @return DecodedToken object containing username, user ID, and role
     * @throws IllegalArgumentException if token verification fails
     */
    public DecodedTokenDto decodeJWT(String token) {
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secretKey)).build();
            DecodedJWT decodedJWT = verifier.verify(token);

            String username = decodedJWT.getSubject();
            if (username == null) {
                throw new UsernameNotFoundException("Token does not contain a subject");
            }

            UserDto currentUser = userService.findByUsername(username);
            System.out.println(currentUser);
            Integer userId = currentUser.getId();
            System.out.println(userId);

            List<String> roles = decodedJWT.getClaim("roles").asList(String.class);
            if (roles == null) {
                roles = Collections.emptyList(); // Default to empty list if no roles claim is present
            }

            String role = roles.get(0);
            if (userId == null) {
                throw new IllegalArgumentException("Token is missing essential information");
            }

            return new DecodedTokenDto(username, userId, role);
        } catch (JWTVerificationException e) {
            throw new IllegalArgumentException("Invalid or expired JWT token", e);
        }
    }

}
