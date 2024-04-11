package wileyt3.backend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

@Mapper(componentModel = "default")
abstract class PasswordMapper {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Named("charArrayToString")
    String charArrayToString(char[] password) {
        if (password == null) return null;
        // Convert char[] to String and hash the password
        String passwordStr = new String(password);
        return passwordEncoder.encode(passwordStr);
    }
}