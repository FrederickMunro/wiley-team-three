package wileyt3.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import wileyt3.backend.dto.CredentialsDto;
import wileyt3.backend.dto.SignUpDto;
import wileyt3.backend.dto.UserDto;
import wileyt3.backend.entity.Role;
import wileyt3.backend.entity.User;
import wileyt3.backend.exception.AppException;
import wileyt3.backend.mapper.UserMapper;
import wileyt3.backend.repository.RoleRepository;
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
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    /**
     * Authenticates a user by their credentials.
     *
     * @param credentialsDto contains the login and password for authentication
     * @return UserDto if authentication is successful
     */
    public UserDto login(CredentialsDto credentialsDto) {
        User user = userRepository.findByUsername(credentialsDto.username())
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
        Optional<User> optionalUser = userRepository.findByUsername(userDto.username());

        if (optionalUser.isPresent()) {
            throw new AppException("Login already exists", HttpStatus.BAD_REQUEST);
        }
        Role defaultRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new AppException("Unknown role", HttpStatus.NOT_FOUND));

        User user = userMapper.signUpToUser(userDto);
        user.setPassword(passwordEncoder.encode(CharBuffer.wrap(userDto.password())));
        user.setRole(userDto.role() == null ? defaultRole : roleRepository.findByName(userDto.role().toUpperCase()).orElseThrow(() -> new AppException("Unknown role", HttpStatus.NOT_FOUND)));

        User savedUser = userRepository.save(user);
        return userMapper.toUserDto(savedUser);
    }

    /**
     * Finds a user by their login.
     *
     * @param userName the user's login
     * @return UserDto containing the user's details
     */
    public UserDto findByUsername(String userName) {
        User user = userRepository.findByUsername(userName)
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));
        return userMapper.toUserDto(user);
    }

    public User authenticateUser(String username, String password) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("Invalid password");
        }
        return user;
    }

}