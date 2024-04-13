package wileyt3.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import wileyt3.backend.dto.StockApiDto;
import wileyt3.backend.entity.Stock;
import wileyt3.backend.entity.Crypto;
import wileyt3.backend.service.StockDataService;
import wileyt3.backend.service.CryptoDataService;

@RestController
@RequiredArgsConstructor
public class AdminController {

    private final StockDataService stockDataService;
    private final CryptoDataService cryptoDataService;

    @PostMapping("/admin/stocks")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Stock> createStock(@RequestParam String symbol) {
        Stock stock = stockDataService.fetchStockData(symbol);
        if (stock != null) {
            stock = stockDataService.saveStock(stock);  // Save the stock to the database
            return ResponseEntity.ok(stock);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/admin/cryptos")
    public ResponseEntity<Crypto> createCrypto(@RequestParam String ticker) {
        Crypto crypto = cryptoDataService.fetchCryptoData(ticker);
        return ResponseEntity.ok(crypto);
    }
}