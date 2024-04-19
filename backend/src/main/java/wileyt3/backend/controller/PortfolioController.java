package wileyt3.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
@CrossOrigin
public class PortfolioController {

    @Autowired
    private PortfolioService portfolioService;

    @PostMapping("/add")
    @PreAuthorize("hasRole('TRADER')")
    @Operation(summary = "Add a stock to a trader's portfolio",
            description = "Allows traders to add a new stock to their portfolio specifying the quantity and purchase price.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Stock added successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request, check input data")
    })
    public ResponseEntity<PortfolioStock> addStockToPortfolio(@Valid @RequestBody PortfolioStockDto portfolioStockDto) {
        try {
            PortfolioStock addedStock = portfolioService.addStockToPortfolio(portfolioStockDto);
            return ResponseEntity.ok(addedStock);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/view/{userId}")
    @PreAuthorize("hasRole('TRADER')")
    @Operation(summary = "View a trader's stocks", description = "Retrieves all stock entries owned by a specific trader.")
    public ResponseEntity<List<PortfolioStock>> viewStocks(@PathVariable Integer userId) {
        List<PortfolioStock> stocks = portfolioService.findByUserId(userId);
        return ResponseEntity.ok(stocks);
    }

    @DeleteMapping("/delete/{portfolioStockId}")
    @PreAuthorize("hasRole('TRADER')")
    @Operation(summary = "Delete a stock from a trader's portfolio", description = "Removes a specific stock from a trader's portfolio.")
    @ApiResponse(responseCode = "200", description = "Stock deleted successfully")
    public ResponseEntity<?> deleteStockFromPortfolio(@PathVariable Integer portfolioStockId) {
        portfolioService.deleteStockFromPortfolio(portfolioStockId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('TRADER')")
    @Operation(summary = "Update stock in a trader's portfolio", description = "Updates the details of a stock in a trader's portfolio.")
    @ApiResponse(responseCode = "200", description = "Stock updated successfully")
    public ResponseEntity<PortfolioStock> updateStockInPortfolio(@Valid @RequestBody PortfolioStockDto portfolioStockDto) {
        PortfolioStock updatedStock = portfolioService.updateStockInPortfolio(portfolioStockDto);
        return ResponseEntity.ok(updatedStock);
    }

    @DeleteMapping("/delete/all/{userId}")
    @PreAuthorize("hasRole('TRADER')")
    @Operation(summary = "Delete all stocks from a trader's portfolio", description = "Removes all stocks from a trader's portfolio.")
    @ApiResponse(responseCode = "200", description = "All stocks deleted successfully")
    public ResponseEntity<?> deleteAllStocksFromPortfolio(@PathVariable Integer userId) {
        portfolioService.deleteAllStocksFromPortfolio(userId);
        return ResponseEntity.ok().build();
    }
}
