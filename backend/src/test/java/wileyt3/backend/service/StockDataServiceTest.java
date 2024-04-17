package wileyt3.backend.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import wileyt3.backend.dto.AllStockApiDto;
import wileyt3.backend.entity.Stock;
import wileyt3.backend.mapper.AllStocksMapper;
import wileyt3.backend.mapper.StockMapper;
import wileyt3.backend.repository.StockRepository;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StockDataServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private StockMapper stockMapper;

    @Mock
    private AllStocksMapper allStocksMapper;

    @Mock
    private StockRepository stockRepository;

    //! Can't be mocking the class that is being tested
    @InjectMocks
    private StockDataService stockDataService;

    @BeforeEach
    public void setup() {
        stockDataService = new StockDataService(restTemplate, stockMapper, allStocksMapper, stockRepository);
    }

    @Test
    public void testFetchAllStocks() {
        // Mocked response data
        AllStockApiDto mockAllStockApiDto = new AllStockApiDto();
        mockAllStockApiDto.setName("Test Stock");
        mockAllStockApiDto.setSymbol("TEST");
        mockAllStockApiDto.setExchange("NYSE");

        AllStockApiDto[] mockedResponseData = new AllStockApiDto[] { mockAllStockApiDto };
        // Setup: Prepare HTTP headers and wrap the response data and headers in a ResponseEntity
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity<AllStockApiDto[]> responseEntity = new ResponseEntity<>(mockedResponseData, headers, HttpStatus.OK);
        // configure the RestTemplate to return the mocked response entity when any GET request is made
        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(HttpEntity.class), eq(AllStockApiDto[].class))).thenReturn(responseEntity);

        //  mock Stock entity to be returned by the mapper
        Stock expectedStock  = new Stock();
        expectedStock.setSymbol("TEST");
        expectedStock.setName("Test Stock");
        expectedStock.setExchange("NYSE");

        // configure the mapper to return the mock Stock entity whenever it is called with any AllStockApiDto
        when(allStocksMapper.allStocksApiDtoToStock(any(AllStockApiDto.class))).thenReturn(expectedStock);

        // Test
        Map<String, Stock> result = stockDataService.fetchAllStocks();

        // Verify
        assertNotNull(result);
        assertEquals(1, result.size());  //expected should be 1

        Stock testStock = result.get("TEST");
        assertNotNull(testStock);
        assertEquals("TEST", testStock.getSymbol());
        assertEquals("Test Stock", testStock.getName());
        assertEquals("NYSE", testStock.getExchange());

        assertFalse(result.containsKey("UNKNOWN_SYMBOL"));

        // Test behavior when the response is empty
        ResponseEntity<AllStockApiDto[]> emptyResponseEntity = new ResponseEntity<>(new AllStockApiDto[0], headers, HttpStatus.OK);
        when(restTemplate.exchange(anyString(), any(), any(), eq(AllStockApiDto[].class))).thenReturn(emptyResponseEntity);
        Map<String, Stock> emptyResult = stockDataService.fetchAllStocks();
        assertEquals(0, emptyResult.size());
    }

}
