package wileyt3.backend.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * A filter that runs once per request to check for JWT in the Authorization header.
 * It validates the token using UserAuthProvider and sets the SecurityContext if valid.
 * The filter applies to all incoming HTTP requests to secure the application.
 */
@RequiredArgsConstructor // Automatically generates a constructor for final fields (dependency injection)
public class JwtAuthFilter extends OncePerRequestFilter {

    private final UserAuthProvider userAuthProvider;
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthFilter.class);


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
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7); // Assuming the token is preceded by "Bearer "
            try {
                Authentication auth = userAuthProvider.validateToken(token);
                SecurityContextHolder.getContext().setAuthentication(auth);
                logger.info("Authentication set for user: {}", auth.getName());
            } catch (Exception e) {
                SecurityContextHolder.clearContext();
                logger.error("Security context cleared due to exception: ", e);
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized: Token validation failed");
                return;
            }
        }
        filterChain.doFilter(request, response); // Proceed with the filter chain
    }
}


