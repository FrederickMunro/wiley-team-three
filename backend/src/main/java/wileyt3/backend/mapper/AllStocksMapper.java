package wileyt3.backend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import wileyt3.backend.dto.AllStockApiDto;
import wileyt3.backend.entity.Stock;

@Mapper(componentModel = "spring")
public interface AllStocksMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "lastPrice", ignore = true)
    Stock allStocksApiDtoToStock(AllStockApiDto allStockApiDto);
}
