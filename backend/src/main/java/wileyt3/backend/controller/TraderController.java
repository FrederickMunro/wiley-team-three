package wileyt3.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wileyt3.backend.entity.Stock;
import wileyt3.backend.service.StockDataService;

import java.util.List;
@RestController
@RequestMapping("/trader")
@RequiredArgsConstructor
@CrossOrigin
public class TraderController {
    private final StockDataService stockDataService;


    @GetMapping("stocks/get-all-db")
    @PreAuthorize("hasRole('TRADER')")
    @Operation(summary = "List all stocks from database (Trader)", description = "Fetches all stocks available from the database. Accessible by TRADER role.")
    public ResponseEntity<List<Stock>> getAllStocksFromDB() {
        try {
            List<Stock> stocks = stockDataService.findAll();
            return ResponseEntity.ok(stocks);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
