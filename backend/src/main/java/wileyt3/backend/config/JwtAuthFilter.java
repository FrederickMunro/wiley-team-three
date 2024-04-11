package wileyt3.backend.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
/**
 * A filter that runs once per request to check for JWT in the Authorization header.
 * It validates the token using UserAuthProvider and sets the SecurityContext if valid.
 * The filter applies to all incoming HTTP requests to secure the application.
 */
@RequiredArgsConstructor // Automatically generates a constructor for final fields (dependency injection)
public class JwtAuthFilter extends OncePerRequestFilter {

    private final UserAuthProvider userAuthProvider;

    /**
     * Filters each HTTP request, checks for JWT, and authenticates the user.
     *
     * @param request     The incoming HttpServletRequest.
     * @param response    The outgoing HttpServletResponse.
     * @param filterChain Represents the chain of filters the request will go through next.
     * @throws ServletException if an exception occurs that interferes with the filter's normal operation.
     * @throws IOException      if an I/O related error has occurred during the processing.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        // Checks for the presence of a JWT in the Authorization header of incoming requests and validates it.
        if (header != null) {
            String[] elements = header.split(" ");
            if (elements.length == 2 && elements[0].equals("Bearer")) {
                try {
                    SecurityContextHolder.getContext().setAuthentication((
                            userAuthProvider.validateToken(elements[1])
                    ));
                } catch (RuntimeException e) {
                    SecurityContextHolder.clearContext(); // Clear security context if token validation fails
                    throw e; // Exception handling if token validation fails
                }
            }
        }
        filterChain.doFilter(request, response); // Proceed with the filter chain
    }

}
