package wileyt3.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import wileyt3.backend.dto.StockApiDto;
import wileyt3.backend.entity.Stock;
import wileyt3.backend.mapper.AllStocksMapper;
import wileyt3.backend.mapper.StockMapper;
import wileyt3.backend.repository.StockRepository;

import java.util.HashMap;
import java.util.Map;

@Service
public class StockDataService {

    @Value("${alpaca.api.key}")
    private String apiKey;

    @Value("${alpaca.api.secret}")
    private String apiSecret;

    @Autowired
    private StockMapper stockMapper;
    @Autowired
    private AllStocksMapper allStockMapper;
    @Autowired
    private StockRepository stockRepository;

    private final RestTemplate restTemplate;

    public StockDataService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Stock fetchStockData(String symbol) {
        String url = "https://paper-api.alpaca.markets/v2/assets/" + symbol;
        HttpHeaders headers = new HttpHeaders();
        headers.set("APCA-API-KEY-ID", apiKey);
        headers.set("APCA-API-SECRET-KEY", apiSecret);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<StockApiDto> response = restTemplate.exchange(url, HttpMethod.GET, entity, StockApiDto.class);
        StockApiDto stockApiDto = response.getBody();
        if (stockApiDto != null) {
            System.out.println("DTO fetched: " + stockApiDto);

            Stock stock = stockMapper.stockApiDtoToStock(stockApiDto);
            System.out.println("Mapped Stock: " + stock);
            return stock;
        } else {
            throw new RuntimeException("Failed to retrieve stock data from API.");
        }
    }

    public Map<String, Stock> fetchAllStocks() {
        String url = "https://paper-api.alpaca.markets/v2/assets";
        HttpHeaders headers = new HttpHeaders();
        headers.set("APCA-API-KEY-ID", apiKey);
        headers.set("APCA-API-SECRET-KEY", apiSecret);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<StockApiDto[]> response = restTemplate.exchange(url, HttpMethod.GET, entity, StockApiDto[].class);
        StockApiDto[] stockApiDtos = response.getBody();
        if (stockApiDtos != null) {
            Map<String, Stock> stocks = new HashMap<>();
            for (StockApiDto dto : stockApiDtos) {
                Stock stock = stockMapper.allStockApiDtoToStock(dto);
                stocks.put(stock.getSymbol(), stock);
            }
            return stocks;
        } else {
            throw new RuntimeException("Failed to retrieve stock data from API.");
        }
    }

    public Stock saveStock(Stock stock) {
        return stockRepository.save(stock);
    }
}
