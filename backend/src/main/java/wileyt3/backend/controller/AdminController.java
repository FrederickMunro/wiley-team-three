package wileyt3.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import wileyt3.backend.entity.Stock;
import wileyt3.backend.service.StockDataService;

import java.util.List;

@RestController
@RequiredArgsConstructor
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
    public ResponseEntity<List<Stock>> getAllStocks() {
        try {
            List<Stock> stocks = stockDataService.findAll();
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




}
