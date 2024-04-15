package wileyt3.backend.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import wileyt3.backend.dto.ErrorDto;

import java.io.IOException;

@Component
public class UserAuthenticationEntryPoint implements AuthenticationEntryPoint {
    /**
     * Defines the entry point for handling authentication errors.
     * This component responds with a JSON payload containing error details when authentication fails.
     */
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    /**
     * Commences an HTTP response when an AuthenticationException is detected.
     * Sets the response status to 401 Unauthorized and crafts a JSON response with an error message.
     *
     * @param request       The request during which the authentication error was detected.
     * @param response      The response being populated with error details.
     * @param authException The exception that caused the entry point to be triggered.
     * @throws IOException      If an input or output exception occurs
     * @throws ServletException If a servlet exception occurs
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        OBJECT_MAPPER.writeValue(response.getOutputStream(), new ErrorDto("User not authorized"));
    }
}
