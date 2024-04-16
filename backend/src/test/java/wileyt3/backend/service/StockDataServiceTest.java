package wileyt3.backend.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import wileyt3.backend.dto.AllStockApiDto;
import wileyt3.backend.dto.StockApiDto;
import wileyt3.backend.entity.Stock;
import wileyt3.backend.mapper.AllStocksMapper;
import wileyt3.backend.mapper.StockMapper;
import wileyt3.backend.repository.StockRepository;

import java.math.BigDecimal;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
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

    @Mock
    private StockDataService stockDataService;

    @BeforeEach
    public void setup() {
        // Mock any required setup before each test
    }

//    @Test
//    public void testFetchStockData() {
//
//        // Mocking
//        StockApiDto mockStockApiDto = new StockApiDto();
//        mockStockApiDto.setName("Test Stock");
//        mockStockApiDto.setSymbol("TEST");
//        mockStockApiDto.setExchange("NYSE");
//
//        StockDataService.StockPrice mockStockPrice = new StockDataService.StockPrice();
//        mockStockPrice.setClose(BigDecimal.valueOf(100.0));
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType( MediaType.APPLICATION_JSON);
//        ResponseEntity<StockApiDto> responseEntity = new ResponseEntity<>(mockStockApiDto, headers, HttpStatus.OK);
//        ResponseEntity<StockDataService.StockPrice[]> tiingoResponseEntity = new ResponseEntity<>(new StockDataService.StockPrice[]{mockStockPrice}, HttpStatus.OK);
//
//        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(HttpEntity.class), eq(StockApiDto.class)))
//                .thenReturn(responseEntity);
//        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(HttpEntity.class), eq(StockDataService.StockPrice[].class)))
//                .thenReturn(tiingoResponseEntity);
//        when(stockMapper.stockApiDtoToStock(any(StockApiDto.class))).thenReturn(new Stock());
//
//        // Test
//        Stock result = stockDataService.fetchStockData("TEST");
//
//        // Verify
//        assertNotNull(result);
//        // Add more assertions as needed
//
//    }

    @Test
    public void testFetchAllStocks_AdminAuthorization() {
        // Mock the authentication process to generate a bearer token
        String bearerToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9..."; // Generated token

        // Mock the HTTP request to retrieve all stocks with the generated token
        String url = "http://localhost:8080/admin/stocks";
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(bearerToken); // Set the bearer token in the authorization header
        HttpEntity<String> entity = new HttpEntity<>(headers);

        AllStockApiDto mockAllStockApiDto = new AllStockApiDto();
        mockAllStockApiDto.setName("Test Stock");
        mockAllStockApiDto.setSymbol("TEST");
        mockAllStockApiDto.setExchange("NYSE");
        AllStockApiDto[] mockedResponseData = new AllStockApiDto[] { mockAllStockApiDto };

        ResponseEntity<AllStockApiDto[]> responseEntity = new ResponseEntity<>(mockedResponseData, HttpStatus.OK);
        lenient().when(restTemplate.exchange(eq(url), eq(HttpMethod.GET), eq(entity), eq(AllStockApiDto[].class)))
                .thenReturn(responseEntity);

        // Test the fetchAllStocks method
        Map<String, Stock> result = stockDataService.fetchAllStocks();

        // Verify the result
        assertNotNull(result);
        assertEquals(1, result.size()); // Check that the size of the map matches the number of elements in the mocked response data

        // Check that each stock object in the map has been correctly mapped from the API DTO
        Stock stock = result.get("TEST");
        assertNotNull(stock);
        assertEquals("Test Stock", stock.getName());
        assertEquals("TEST", stock.getSymbol());
        assertEquals("NYSE", stock.getExchange());

    }

}
