package wileyt3.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
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
    @Operation(summary = "Create a stock entry", description = "Creates a new stock entry in the system using the provided symbol to fetch data.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Stock created successfully"),
            @ApiResponse(responseCode = "404", description = "Stock not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error during stock creation")
    })
    public ResponseEntity<Stock> createStock(@RequestParam String symbol) {
        try {
            Stock stock = stockDataService.fetchStockData(symbol);
            if (stock != null) {
                return ResponseEntity.ok(stockDataService.saveStock(stock));
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/admin/stocks")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "List all stocks", description = "Retrieves a paginated list of all stocks in the system.")
    public ResponseEntity<Page<Stock>> getAllStocks(Pageable pageable) {
        try {
            return ResponseEntity.ok(stockDataService.findAll(pageable));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/admin/stocks/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get a stock by ID", description = "Retrieves a stock based on its unique identifier.")
    public ResponseEntity<Stock> getStock(@PathVariable Integer id) {
        try {
            Stock stock = stockDataService.findById(id);
            return stock != null ? ResponseEntity.ok(stock) : ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/admin/stocks/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update a stock", description = "Updates a stock entry based on the provided ID.")
    public ResponseEntity<Stock> updateStock(@PathVariable Integer id) {
        try {
            Stock stock = stockDataService.updateStock(id);
            return ResponseEntity.ok(stock);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/admin/stocks/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete a stock", description = "Deletes a stock from the system based on the provided ID.")
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
    @Operation(summary = "List all stocks from external service", description = "Fetches all stocks available from the external stock data service.")
    public ResponseEntity<List<Stock>> getAllStocks() {
        try {
            Map<String, Stock> stockMap = stockDataService.fetchAllStocks();
            List<Stock> stocks = new ArrayList<>(stockMap.values());
            return ResponseEntity.ok(stocks);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
