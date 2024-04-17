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
import java.util.Map;
import java.util.HashMap;

/**
 * Service class for handling market data of stocks.
 * Implements the MarketDataService for operations related to stocks.
 */
@Service
public class StockDataService implements MarketDataService<Stock> {

    @Value("${alpaca.api.key}")
    private String alpacaApiKey;

    @Value("${alpaca.api.secret}")
    private String alpacaApiSecret;

    @Value("${tiingo.api.token}")
    private String tiingoToken;

    private final StockMapper stockMapper;
    private AllStocksMapper allStocksMapper;

    private final StockRepository stockRepository;
    private final RestTemplate restTemplate;

    @Autowired
    public StockDataService(RestTemplate restTemplate, StockMapper stockMapper, AllStocksMapper allStocksMapper, StockRepository stockRepository) {
        this.restTemplate = restTemplate;
        this.stockMapper = stockMapper;
        this.allStocksMapper = allStocksMapper;
        this.stockRepository = stockRepository;
    }

    /**
     * Fetches market data for a specific stock identified by its symbol.
     *
     * @param symbol The stock symbol to fetch data for.
     * @return Stock object populated with fetched data.
     */
    @Override
    public Stock fetchMarketData(String symbol) {
        StockApiDto stockApiDto = fetchStockDataFromAlpaca(symbol);
        BigDecimal closingPrice = fetchClosingPriceFromTiingo(symbol);
        return mapToStock(stockApiDto, closingPrice);
    }

    private StockApiDto fetchStockDataFromAlpaca(String symbol) {
        String url = "https://paper-api.alpaca.markets/v2/assets/" + symbol;
        ResponseEntity<StockApiDto> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                new HttpEntity<>(createAlpacaHeaders()),
                StockApiDto.class
        );
        return response.getBody();
    }

    private BigDecimal fetchClosingPriceFromTiingo(String symbol) {
        String url = "https://api.tiingo.com/tiingo/daily/" + symbol + "/prices";
        ResponseEntity<StockPrice[]> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                new HttpEntity<>(createTiingoHeaders()),
                StockPrice[].class
        );
        return response.getBody() != null && response.getBody().length > 0 ? response.getBody()[0].getClose() : null;
    }

    private HttpHeaders createAlpacaHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("APCA-API-KEY-ID", alpacaApiKey);
        headers.set("APCA-API-SECRET-KEY", alpacaApiSecret);
        return headers;
    }

    private HttpHeaders createTiingoHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", tiingoToken);
        return headers;
    }

    private Stock mapToStock(StockApiDto stockApiDto, BigDecimal closingPrice) {
        Stock stock = stockMapper.stockApiDtoToStock(stockApiDto);
        stock.setLastPrice(closingPrice);
        return stock;
    }

    /**
     * Fetches all available market data for stocks from Alpaca.
     *
     * @return Map of stock symbols to Stock objects.
     */
    @Override
    public Map<String, Stock> fetchAllMarketData() {
        String url = "https://paper-api.alpaca.markets/v2/assets";
        ResponseEntity<AllStockApiDto[]> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                new HttpEntity<>(createAlpacaHeaders()),
                AllStockApiDto[].class
        );
        return mapToStocks(response.getBody());
    }

    private Map<String, Stock> mapToStocks(AllStockApiDto[] dtos) {
        Map<String, Stock> stocks = new HashMap<>();
        if (dtos != null) {
            for (AllStockApiDto dto : dtos) {
                Stock stock = allStocksMapper.allStocksApiDtoToStock(dto);
                stocks.put(stock.getSymbol(), stock);
            }
        }
        return stocks;
    }

    /**
     * Saves the given stock data to the repository.
     *
     * @param data The stock data to save.
     * @return The saved Stock object.
     */
    @Override
    public Stock saveMarketData(Stock data) {
        return stockRepository.save(data);
    }

    /**
     * Finds a stock by its database identifier.
     *
     * @param id The ID of the stock to find.
     * @return The found Stock or null if not found.
     */
    @Override
    public Stock findById(Integer id) {
        return stockRepository.findById(id).orElse(null);
    }

    /**
     * Returns a pageable list of all stocks in the database.
     *
     * @param pageable The pageable object specifying the pagination and sorting.
     * @return Page of Stock.
     */
    @Override
    public Page<Stock> findAll(Pageable pageable) {
        return stockRepository.findAll(pageable);
    }

    /**
     * Returns a list of all stocks in the database.
     *
     * @return List of Stock.
     */
    @Override
    public List<Stock> findAll() {
        return stockRepository.findAll();
    }

    /**
     * Updates market data for a given stock by its ID.
     *
     * @param id The ID of the stock to update.
     * @return The updated Stock object.
     */
    @Override
    public Stock updateMarketData(Integer id) {
        Stock existingStock = findById(id);
        if (existingStock == null) {
            throw new IllegalArgumentException("Stock not found with ID: " + id);
        }
        Stock updatedData = fetchMarketData(existingStock.getSymbol());
        existingStock.updateFrom(updatedData);
        return stockRepository.save(existingStock);
    }

    /**
     * Deletes a stock from the database by its ID.
     *
     * @param id The ID of the stock to delete.
     */
    @Override
    public void deleteMarketData(Integer id) {
        stockRepository.deleteById(id);
    }

    @Setter
    @Getter
    private static class StockPrice {
        private BigDecimal close;
    }
}
