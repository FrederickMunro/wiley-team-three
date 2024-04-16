package wileyt3.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import wileyt3.backend.entity.Stock;
import wileyt3.backend.service.StockDataService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class AdminController {

    private final StockDataService stockDataService;

    @PostMapping("/admin/stocks")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Stock> createStock(@RequestParam String symbol) {
        try {
            Stock stock = stockDataService.fetchStockData(symbol);
            if (stock != null) {
                stock = stockDataService.saveStock(stock);  // Save the stock to the database
                System.out.println("Stock saved: " + stock);
                return ResponseEntity.ok(stock);
            } else {
                System.out.println("Stock not found after fetching");
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            System.err.println("Error processing stock creation: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/admin/allstocks")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Stock>> getAllStocks() {
        Map<String, Stock> stockMap = stockDataService.fetchAllStocks();
        List<Stock> stocks = new ArrayList<>(stockMap.values());
        return ResponseEntity.ok(stocks);
    }
}

