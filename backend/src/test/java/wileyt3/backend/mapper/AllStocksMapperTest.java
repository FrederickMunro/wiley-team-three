package wileyt3.backend.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import wileyt3.backend.dto.AllStockApiDto;
import wileyt3.backend.entity.Stock;

class AllStocksMapperTest {

    private AllStocksMapper mapper = Mappers.getMapper(AllStocksMapper.class);

    @Test
    void testMapping() {
        AllStockApiDto dto = new AllStockApiDto();
        dto.setSymbol("AAPL");
        dto.setName("Apple Inc.");
        dto.setExchange("NASDAQ");
        dto.setClassType("Equity");

        Stock stock = mapper.allStocksApiDtoToStock(dto);

        assertEquals("AAPL", stock.getSymbol());
        assertEquals("Apple Inc.", stock.getName());
        assertEquals("NASDAQ", stock.getExchange());
        // Verify that ignored or not included fields are handled correctly
        assertNull(stock.getId());
        assertNull(stock.getLastPrice());
    }
}
