package wileyt3.backend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import wileyt3.backend.dto.PortfolioCryptoDto;
import wileyt3.backend.entity.PortfolioCrypto;
import wileyt3.backend.entity.User;
import wileyt3.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class PortfolioCryptoMapper {

    @Autowired
    protected UserRepository userRepository;

    @Mapping(target = "user", source = "userId", qualifiedByName = "userIdToUser")
    public abstract PortfolioCrypto dtoToPortfolioCrypto(PortfolioCryptoDto dto);

    @Mapping(target = "userId", source = "user.id")
    public abstract PortfolioCryptoDto portfolioCryptoToDto(PortfolioCrypto portfolioCrypto);

    @Named("userIdToUser")
    protected User userIdToUser(Integer id) {
        return userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));
    }
}
