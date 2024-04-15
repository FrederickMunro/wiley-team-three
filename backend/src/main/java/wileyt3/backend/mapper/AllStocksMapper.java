package wileyt3.backend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import wileyt3.backend.dto.StockApiDto;
import wileyt3.backend.entity.Stock;

@Mapper(componentModel = "spring")
public interface AllStocksMapper {
    @Mapping(target = "id", ignore = true) // we don't want to map the ID from DTO
    @Mapping(target = "lastPrice", ignore = true) // lastPrice is not available in the DTO
    Stock allStocksApiDtoToStock(StockApiDto stockApiDto);
}
