package wileyt3.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import wileyt3.backend.entity.Stock;
import wileyt3.backend.service.StockDataService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/stocks")
@RequiredArgsConstructor
public class StockController {
    private final StockDataService stockDataService;

    @PostMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create a stock (ADMIN)", description = "Creates a new stock entry in the system using the provided symbol to fetch data. Ensure to include the 'ADMIN' role's token in the Authorization header.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Stock created successfully"),
            @ApiResponse(responseCode = "404", description = "Stock not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error during stock creation")
    })
    public ResponseEntity<Stock> createStock(@RequestParam String symbol) {
        try {
            Stock stock = stockDataService.fetchMarketData(symbol);
            if (stock != null) {
                return ResponseEntity.ok(stockDataService.saveMarketData(stock));
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get a stock by ID (ADMIN)", description = "Retrieves a stock based on its unique identifier. Ensure to include the 'ADMIN' role's token in the Authorization header.")
    public ResponseEntity<Stock> getStock(@PathVariable Integer id) {
        try {
            Stock stock = stockDataService.findById(id);
            return stock != null ? ResponseEntity.ok(stock) : ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update a stock (ADMIN)", description = "Updates a stock entry based on the provided ID. Ensure to include the 'ADMIN' role's token in the Authorization header.")
    public ResponseEntity<Stock> updateStock(@PathVariable Integer id) {
        try {
            Stock updatedStock = stockDataService.updateMarketData(id);
            return ResponseEntity.ok(updatedStock);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete a stock (ADMIN)", description = "Deletes a stock from the system based on the provided ID. Ensure to include the 'ADMIN' role's token in the Authorization header.")
    public ResponseEntity<Void> deleteStock(@PathVariable Integer id) {
        try {
            stockDataService.deleteMarketData(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

        }
    }

    @GetMapping("/get-all-api")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "List all stocks from external service (ADMIN)", description = "Fetches all stocks available from the external stock data service.")
    public ResponseEntity<List<Stock>> getAllStocksFromAPI() {
        try {
            Map<String, Stock> stockMap = stockDataService.fetchAllMarketData();
            List<Stock> stocks = new ArrayList<>(stockMap.values());
            return ResponseEntity.ok(stocks);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/get-all-db")
    @PreAuthorize("hasRole('TRADER')")
    @Operation(summary = "List all stocks from database (TRADER)", description = "Fetches all stocks available from the database.")
    public ResponseEntity<List<Stock>> getAllStocksFromDB() {
        try {
            List<Stock> stocks = stockDataService.findAll();
            return ResponseEntity.ok(stocks);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
