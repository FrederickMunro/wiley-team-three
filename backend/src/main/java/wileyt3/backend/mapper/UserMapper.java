package wileyt3.backend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import wileyt3.backend.dto.SignUpDto;
import wileyt3.backend.dto.UserDto;
import wileyt3.backend.entity.User;
/**
 * Mapper for converting between User entity and UserDto.
 * To use when transferring user info between persistence layer and service layer
 */
@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toUserDto(User user);

    @Mapping(target = "password", ignore = true)  // Password should not be directly mapped
    User signUpToUser(SignUpDto signUpDto);
}