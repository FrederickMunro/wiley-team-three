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
import wileyt3.backend.dto.AllStockApiDto;
import wileyt3.backend.dto.StockApiDto;
import wileyt3.backend.entity.Stock;
import wileyt3.backend.mapper.AllStocksMapper;
import wileyt3.backend.mapper.StockMapper;
import wileyt3.backend.repository.StockRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

@Service
public class StockDataService implements MarketDataService<Stock> {

    @Value("${alpaca.api.key}")
    private String apiKey;

    @Value("${alpaca.api.secret}")
    private String apiSecret;
    @Value("${tiingo.api.token}")
    private String tiingoToken;

    private StockMapper stockMapper;
    private AllStocksMapper allStocksMapper;
    private StockRepository stockRepository;

    private final RestTemplate restTemplate;

    @Autowired
    public StockDataService(RestTemplate restTemplate, StockMapper stockMapper, AllStocksMapper allStocksMapper, StockRepository stockRepository) {
        this.restTemplate = restTemplate;
        this.stockMapper = stockMapper;
        this.allStocksMapper = allStocksMapper;
        this.stockRepository = stockRepository;
    }

    @Override
    public Stock fetchMarketData(String identifier) {
        String alpacaUrl = "https://paper-api.alpaca.markets/v2/assets/" + identifier;
        HttpHeaders alpacaHeaders = new HttpHeaders();
        alpacaHeaders.set("APCA-API-KEY-ID", apiKey);
        alpacaHeaders.set("APCA-API-SECRET-KEY", apiSecret);
        HttpEntity<String> alpacaEntity = new HttpEntity<>(alpacaHeaders);
        ResponseEntity<StockApiDto> alpacaResponse = restTemplate.exchange(alpacaUrl, HttpMethod.GET, alpacaEntity, StockApiDto.class);

        if (alpacaResponse.getBody() != null) {
            StockApiDto stockApiDto = alpacaResponse.getBody();
            System.out.println("DTO fetched from Alpaca: " + stockApiDto);

            // Fetching the closing price from Tiingo
            String tiingoUrl = "https://api.tiingo.com/tiingo/daily/" + identifier + "/prices";
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

    @Override
    public Map<String, Stock> fetchAllMarketData() {
        String url = "https://paper-api.alpaca.markets/v2/assets";
        HttpHeaders headers = new HttpHeaders();
        headers.set("APCA-API-KEY-ID", apiKey);
        headers.set("APCA-API-SECRET-KEY", apiSecret);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<AllStockApiDto[]> response = restTemplate.exchange(url, HttpMethod.GET, entity, AllStockApiDto[].class);
        AllStockApiDto[] allStockApiDtos = response.getBody();
        if (allStockApiDtos != null) {
            Map<String, Stock> stocks = new HashMap<>();
            for (AllStockApiDto dto : allStockApiDtos) {
                Stock stock = allStocksMapper.allStocksApiDtoToStock(dto);
                stocks.put(stock.getSymbol(), stock);
            }
            return stocks;
        } else {
            throw new RuntimeException("Failed to retrieve stock data from API.");
        }
    }

    @Override
    public Stock saveMarketData(Stock data) {
        return stockRepository.save(data);
    }
    @Override
    public Stock findById(Integer id) {
        return stockRepository.findById(id).orElse(null);
    }
    // Allows clients to specify the size of the page (size parameter),
    // the page number (page parameter),
    // and the sorting criteria (sort parameter)
    @Override
    public Page<Stock> findAll(Pageable pageable) {
        return stockRepository.findAll(pageable);
    }

    @Override
    public List<Stock> findAll() {
        return stockRepository.findAll();
    }

    @Override
    public Stock updateMarketData(Integer id) {
        Stock stock = findById(id);
        if (stock == null) {
            throw new RuntimeException("Stock not found.");
        }
        Stock updatedStock = fetchMarketData(stock.getSymbol());
        // Update fields
        stock.setSymbol(updatedStock.getSymbol());
        stock.setName(updatedStock.getName());
        stock.setExchange(updatedStock.getExchange());
        stock.setLastPrice(updatedStock.getLastPrice());
        return stockRepository.save(stock);
    }

    @Override
    public void deleteMarketData(Integer id) {
        stockRepository.deleteById(id);
    }

    // TODO: Add update/fetch all if we want to update prices for all stocks.
    @Setter
    @Getter
    static class StockPrice {
        private BigDecimal close;
    }
}
