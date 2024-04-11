package wileyt3.backend.dto;

public record SignUpDto(String firstName, String lastName, String login, char[] password) {
}