package wileyt3.backend.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import wileyt3.backend.dto.PortfolioStockDto;
import wileyt3.backend.entity.PortfolioStock;
import wileyt3.backend.service.PortfolioService;

import java.util.List;

@RestController
@RequestMapping("/portfolio")
@Validated
public class PortfolioController {

    @Autowired
    private PortfolioService portfolioService;

    @PostMapping("/add")
    @PreAuthorize("hasRole('TRADER')")
    public ResponseEntity<PortfolioStock> addStockToPortfolio(@Valid@RequestBody PortfolioStockDto portfolioStockDto) {
        try {
            PortfolioStock addedStock = portfolioService.addStockToPortfolio(portfolioStockDto);
            return ResponseEntity.ok(addedStock);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/view/{userId}")
    @PreAuthorize("hasRole('TRADER')")
    public ResponseEntity<List<PortfolioStock>> viewStocks(@PathVariable Integer userId) {
        List<PortfolioStock> stocks = portfolioService.findByUserId(userId);
        return ResponseEntity.ok(stocks);
    }

    @DeleteMapping("/delete/{portfolioStockId}")
    @PreAuthorize("hasRole('TRADER')")
    public ResponseEntity<?> deleteStockFromPortfolio(@PathVariable Integer portfolioStockId) {
        portfolioService.deleteStockFromPortfolio(portfolioStockId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('TRADER')")
    public ResponseEntity<PortfolioStock> updateStockInPortfolio(@Valid @RequestBody PortfolioStockDto portfolioStockDto) {
        PortfolioStock updatedStock = portfolioService.updateStockInPortfolio(portfolioStockDto);
        return ResponseEntity.ok(updatedStock);
    }

    @DeleteMapping("/delete/all/{userId}")
    @PreAuthorize("hasRole('TRADER')")
    public ResponseEntity<?> deleteAllStocksFromPortfolio(@PathVariable Integer userId) {
        portfolioService.deleteAllStocksFromPortfolio(userId);
        return ResponseEntity.ok().build();
    }


}
