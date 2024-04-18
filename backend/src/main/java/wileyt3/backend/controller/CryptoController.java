package wileyt3.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import wileyt3.backend.dto.HistoricalCryptoDto;
import wileyt3.backend.service.HistoricalCryptoService;

import java.util.List;

@RestController
@RequestMapping("/analyst/crypto")
@Validated
public class CryptoController {

    private final HistoricalCryptoService historicalCryptoService;

    public CryptoController(HistoricalCryptoService historicalCryptoService) {
        this.historicalCryptoService = historicalCryptoService;
    }

    @GetMapping("/historical")
    @PreAuthorize("hasRole('ANALYST')")
    @Operation(
            summary = "Retrieve historical cryptocurrency data (ANALYST)",
            description = "Fetches historical price data for specified cryptocurrencies using Tiingo APIs.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful retrieval of data",
                            content = @Content(schema = @Schema(implementation = HistoricalCryptoDto.class))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "404", description = "Data not found"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    public ResponseEntity<List<HistoricalCryptoDto>> getAllHistoricalCryptoData(
            @RequestParam @NotBlank String tickers,
            @RequestParam @NotBlank String startDate,
            @RequestParam @NotBlank String resampleFreq) {

        List<HistoricalCryptoDto> historicalCryptoData = historicalCryptoService.fetchHistoricalCryptoData(tickers, startDate, resampleFreq);
        return ResponseEntity.ok(historicalCryptoData);
    }
}