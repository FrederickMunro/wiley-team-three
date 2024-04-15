package wileyt3.backend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Mapper for handling password transformations.
 * Uses Spring's PasswordEncoder to encrypt passwords.
 */
@Mapper(componentModel = "default")
abstract class PasswordMapper {

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Encodes a character array to a hashed string using the configured PasswordEncoder.
     *
     * @param password the character array to encode
     * @return a hashed string of the password
     */
    @Named("charArrayToString")
    String charArrayToString(char[] password) {
        if (password == null) return null;
        // Convert char[] to String and hash the password
        String passwordStr = new String(password);
        return passwordEncoder.encode(passwordStr);
    }
}