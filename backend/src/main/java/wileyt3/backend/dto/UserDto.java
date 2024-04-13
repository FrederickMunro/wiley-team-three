package wileyt3.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import wileyt3.backend.entity.Role;

/**
 * DTO for User representing user details.
 * This class is used to pass user information between the client and server layers.
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserDto {
    private Integer id;
    private String username;
    private String email;
    private String token;
    private Role role;
}
