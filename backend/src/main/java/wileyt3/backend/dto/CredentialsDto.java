package wileyt3.backend.dto;

/**
 * DTO for user credentials.
 */
public record CredentialsDto(String username, char[] password) {
    // Record is immutable (no setters), has all args constructor, getters, equals, hashcode, toString
}