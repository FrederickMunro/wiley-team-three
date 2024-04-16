package wileyt3.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    @GetMapping("/admin/stocks")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<Stock>> getAllStocks(Pageable pageable) {
        try {
            Page<Stock> stocks = stockDataService.findAll(pageable);
            return ResponseEntity.ok(stocks);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/admin/stocks/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Stock> getStock(@PathVariable Integer id) {
        Stock stock = stockDataService.findById(id);
        if (stock != null) {
            return ResponseEntity.ok(stock);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // In AdminController
    @PutMapping("/admin/stocks/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Stock> updateStock(@PathVariable Integer id) {
        try {
            Stock stock = stockDataService.updateStock(id);
            return ResponseEntity.ok(stock);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // In AdminController
    @DeleteMapping("/admin/stocks/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteStock(@PathVariable Integer id) {
        try {
            stockDataService.deleteStock(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
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

