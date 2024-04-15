package wileyt3.backend.dto;

/**
 * DTO for user registration details.
 */
public record SignUpDto(String username, String email, char[] password) {
    // Record is immutable (no setters), has all args constructor, getters, equals, hashcode, toString
}