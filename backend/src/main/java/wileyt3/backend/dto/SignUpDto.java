package wileyt3.backend.dto;

/**
 * DTO for user registration details.
 */
public record SignUpDto(String firstName, String lastName, String login, char[] password) {
    // Record is immutable (no setters), has all args constructor, getters, equals, hashcode, toString
}