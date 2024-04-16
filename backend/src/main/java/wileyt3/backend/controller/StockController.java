package wileyt3.backend.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import wileyt3.backend.entity.Stock;
import wileyt3.backend.service.StockDataService;

import java.util.List;

@RestController
@AllArgsConstructor
public class StockController {

    private final StockDataService stockDataService;


    @GetMapping("/stocks")
    public ResponseEntity<List<Stock>> getAllStocks() {
        try {
            List<Stock> stocks = stockDataService.findAll();
            return ResponseEntity.ok(stocks);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
