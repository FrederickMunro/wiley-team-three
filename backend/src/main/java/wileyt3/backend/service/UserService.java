package wileyt3.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import wileyt3.backend.dto.CredentialsDto;
import wileyt3.backend.dto.SignUpDto;
import wileyt3.backend.dto.UserDto;
import wileyt3.backend.entity.User;
import wileyt3.backend.exception.AppException;
import wileyt3.backend.mapper.UserMapper;
import wileyt3.backend.repository.UserRepository;

import java.nio.CharBuffer;
import java.util.Optional;

/**
 * Service class for managing user-related operations such as registration and login.
 */
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final UserMapper userMapper;

    /**
     * Authenticates a user by their credentials.
     *
     * @param credentialsDto contains the login and password for authentication
     * @return UserDto if authentication is successful
     */
    public UserDto login(CredentialsDto credentialsDto) {
        User user = userRepository.findByLogin(credentialsDto.login())
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));

        if (passwordEncoder.matches(CharBuffer.wrap(credentialsDto.password()), user.getPassword())) {
            return userMapper.toUserDto(user);
        }
        throw new AppException("Invalid password", HttpStatus.BAD_REQUEST);
    }

    /**
     * Registers a new user into the system.
     *
     * @param userDto contains user registration information
     * @return UserDto of the newly created user
     */
    public UserDto register(SignUpDto userDto) {
        Optional<User> optionalUser = userRepository.findByLogin(userDto.login());

        if (optionalUser.isPresent()) {
            throw new AppException("Login already exists", HttpStatus.BAD_REQUEST);
        }

        User user = userMapper.signUpToUser(userDto);
        user.setPassword(passwordEncoder.encode(CharBuffer.wrap(userDto.password())));

        User savedUser = userRepository.save(user);

        return userMapper.toUserDto(savedUser);
    }

    /**
     * Finds a user by their login.
     *
     * @param login the user's login
     * @return UserDto containing the user's details
     */
    public UserDto findByLogin(String login) {
        User user = userRepository.findByLogin(login)
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));
        return userMapper.toUserDto(user);
    }

}