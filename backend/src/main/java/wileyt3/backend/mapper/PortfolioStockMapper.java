package wileyt3.backend.mapper;

import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import wileyt3.backend.dto.PortfolioStockDto;
import wileyt3.backend.entity.PortfolioStock;
import wileyt3.backend.entity.User;
import wileyt3.backend.repository.UserRepository;

@Mapper(componentModel = "spring")
public abstract class PortfolioStockMapper {

    @Autowired
    protected UserRepository userRepository;

    @Mapping(target = "user", source = "userId", qualifiedByName = "userIdToUser")
    public abstract PortfolioStock dtoToPortfolioStock(PortfolioStockDto dto);

    @Mapping(target = "userId", source = "user.id")
    public abstract PortfolioStockDto portfolioStockToDto(PortfolioStock portfolioStock);

    @Named("userIdToUser")
    protected User userIdToUser(Integer id) {
        return userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));
    }
}
