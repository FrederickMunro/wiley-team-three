package wileyt3.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * DTO for error details.
 */
@AllArgsConstructor
@Builder
@Data
public class ErrorDto {
    private String message;
}
