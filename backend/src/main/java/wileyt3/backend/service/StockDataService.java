package wileyt3.backend.service;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import wileyt3.backend.dto.StockApiDto;
import wileyt3.backend.entity.Stock;
import wileyt3.backend.mapper.StockMapper;
import wileyt3.backend.repository.StockRepository;

import java.math.BigDecimal;

@Service
public class StockDataService {

    @Value("${alpaca.api.key}")
    private String apiKey;

    @Value("${alpaca.api.secret}")

    private String apiSecret;
    @Value("${tiingo.api.token}")
    private String tiingoToken;

    @Autowired
    private StockMapper stockMapper;
    @Autowired
    private StockRepository stockRepository;

    private final RestTemplate restTemplate;

    public StockDataService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Stock fetchStockData(String symbol) {
        String alpacaUrl = "https://paper-api.alpaca.markets/v2/assets/" + symbol;
        HttpHeaders alpacaHeaders = new HttpHeaders();
        alpacaHeaders.set("APCA-API-KEY-ID", apiKey);
        alpacaHeaders.set("APCA-API-SECRET-KEY", apiSecret);
        HttpEntity<String> alpacaEntity = new HttpEntity<>(alpacaHeaders);
        ResponseEntity<StockApiDto> alpacaResponse = restTemplate.exchange(alpacaUrl, HttpMethod.GET, alpacaEntity, StockApiDto.class);

        if (alpacaResponse.getBody() != null) {
            StockApiDto stockApiDto = alpacaResponse.getBody();
            System.out.println("DTO fetched from Alpaca: " + stockApiDto);

            // Fetching the closing price from Tiingo
            String tiingoUrl = "https://api.tiingo.com/tiingo/daily/" + symbol + "/prices";
            HttpHeaders tiingoHeaders = new HttpHeaders();
            tiingoHeaders.set("Authorization", tiingoToken);
            HttpEntity<String> tiingoEntity = new HttpEntity<>(tiingoHeaders);
            ResponseEntity<StockPrice[]> tiingoResponse = restTemplate.exchange(tiingoUrl, HttpMethod.GET, tiingoEntity, StockPrice[].class);

            if (tiingoResponse.getBody() != null && tiingoResponse.getBody().length > 0) {
                StockPrice stockPrice = tiingoResponse.getBody()[0];
                System.out.println("Closing price fetched from Tiingo: " + stockPrice.getClose());

                // Map Alpaca API DTO to Stock object and set the closing price
                Stock stock = stockMapper.stockApiDtoToStock(stockApiDto);
                stock.setLastPrice(stockPrice.getClose());
                System.out.println("Mapped Stock with price: " + stock);
                return stock;
            } else {
                throw new RuntimeException("Failed to retrieve stock price data from Tiingo.");
            }
        } else {
            throw new RuntimeException("Failed to retrieve stock data from Alpaca.");
        }
    }

    public Stock saveStock(Stock stock) {
        return stockRepository.save(stock);
    }

    public Stock findById(Integer id) {
        return stockRepository.findById(id).orElse(null);
    }

    // Allows clients to specify the size of the page (size parameter),
    // the page number (page parameter),
    // and the sorting criteria (sort parameter)
    public Page<Stock> findAll(Pageable pageable) {
        return stockRepository.findAll(pageable);
    }

    public Stock updateStock(Integer id) {
        Stock stock = findById(id);
        if (stock == null) {
            throw new RuntimeException("Stock not found.");
        }
        Stock updatedStock = fetchStockData(stock.getSymbol());
        // Update fields
        stock.setSymbol(updatedStock.getSymbol());
        stock.setName(updatedStock.getName());
        stock.setExchange(updatedStock.getExchange());
        stock.setLastPrice(updatedStock.getLastPrice());
        return stockRepository.save(stock);
    }

    public void deleteStock(Integer id) {
        stockRepository.deleteById(id);
    }

    @Setter
    @Getter
    static class StockPrice {
        private BigDecimal close;
    }
}
