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
import wileyt3.backend.dto.PortfolioCryptoDto;
import wileyt3.backend.entity.PortfolioCrypto;
import wileyt3.backend.service.PortfolioCryptoService;

import java.util.List;

@RestController
@RequestMapping("/portfolio/crypto")
@Validated
@CrossOrigin
public class PortfolioCryptoController {

    @Autowired
    private PortfolioCryptoService portfolioCryptoService;

    @PostMapping("/add")
    @PreAuthorize("hasRole('TRADER')")
    @Operation(summary = "Add a crypto to a trader's portfolio",
            description = "Allows traders to add a new cryptocurrency to their portfolio specifying the quantity and purchase price.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Crypto added successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request, check input data")
    })
    public ResponseEntity<PortfolioCrypto> addCryptoToPortfolio(@Valid @RequestBody PortfolioCryptoDto portfolioCryptoDto) {
        try {
            PortfolioCrypto addedCrypto = portfolioCryptoService.addCryptoToPortfolio(portfolioCryptoDto);
            return ResponseEntity.ok(addedCrypto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/view/{userId}")
    @PreAuthorize("hasRole('TRADER')")
    @Operation(summary = "View a trader's cryptos", description = "Retrieves all crypto entries owned by a specific trader.")
    public ResponseEntity<List<PortfolioCrypto>> viewCryptos(@PathVariable Integer userId) {
        List<PortfolioCrypto> cryptos = portfolioCryptoService.findByUserId(userId);
        return ResponseEntity.ok(cryptos);
    }

    @DeleteMapping("/delete/{portfolioCryptoId}")
    @PreAuthorize("hasRole('TRADER')")
    @Operation(summary = "Delete a crypto from a trader's portfolio", description = "Removes a specific crypto from a trader's portfolio.")
    @ApiResponse(responseCode = "200", description = "Crypto deleted successfully")
    public ResponseEntity<?> deleteCryptoFromPortfolio(@PathVariable Integer portfolioCryptoId) {
        portfolioCryptoService.deleteCryptoFromPortfolio(portfolioCryptoId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('TRADER')")
    @Operation(summary = "Update crypto in a trader's portfolio", description = "Updates the details of a crypto in a trader's portfolio.")
    @ApiResponse(responseCode = "200", description = "Crypto updated successfully")
    public ResponseEntity<PortfolioCrypto> updateCryptoInPortfolio(@Valid @RequestBody PortfolioCryptoDto portfolioCryptoDto) {
        PortfolioCrypto updatedCrypto = portfolioCryptoService.updateCryptoInPortfolio(portfolioCryptoDto);
        return ResponseEntity.ok(updatedCrypto);
    }

    @DeleteMapping("/delete/all/{userId}")
    @PreAuthorize("hasRole('TRADER')")
    @Operation(summary = "Delete all cryptos from a trader's portfolio", description = "Removes all cryptos from a trader's portfolio.")
    @ApiResponse(responseCode = "200", description = "All cryptos deleted successfully")
    public ResponseEntity<?> deleteAllCryptosFromPortfolio(@PathVariable Integer userId) {
        portfolioCryptoService.deleteAllCryptosFromPortfolio(userId);
        return ResponseEntity.ok().build();
    }
}
