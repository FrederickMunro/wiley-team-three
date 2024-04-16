package wileyt3.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import wileyt3.backend.dto.PortfolioStockDto;
import wileyt3.backend.entity.PortfolioStock;
import wileyt3.backend.service.PortfolioService;

@RestController
@RequestMapping("/portfolio")
public class PortfolioController {

    @Autowired
    private PortfolioService portfolioService;

    @PostMapping("/add")
    @PreAuthorize("hasRole('TRADER')")
    public ResponseEntity<PortfolioStock> addStockToPortfolio(@RequestBody PortfolioStockDto portfolioStockDto) {
        try {
            PortfolioStock addedStock = portfolioService.addStockToPortfolio(
                    portfolioStockDto.getUserId(),
                    portfolioStockDto.getSymbol(),
                    portfolioStockDto.getQuantity(),
                    portfolioStockDto.getPurchasePrice());
            return ResponseEntity.ok(addedStock);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
