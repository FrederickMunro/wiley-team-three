package wileyt3.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import wileyt3.backend.dto.HistoricalCryptoDto;
import wileyt3.backend.service.HistoricalCryptoService;

import java.util.List;

@RestController
@RequestMapping("/admin/crypto")
public class CryptoController {

    private final HistoricalCryptoService historicalCryptoService;

    public CryptoController(HistoricalCryptoService historicalCryptoService) {
        this.historicalCryptoService = historicalCryptoService;
    }

    @GetMapping("/historical")
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<HistoricalCryptoDto>> getAllHistoricalCryptoData(@RequestParam String tickers, @RequestParam String startDate, @RequestParam String resampleFreq) {

        String url = "https://api.tiingo.com/tiingo/crypto/prices" + "?tickers=" + tickers + "&startDate=" + startDate + "&resampleFreq=" + resampleFreq;

        List<HistoricalCryptoDto> historicalCryptoData = historicalCryptoService.fetchHistoricalCryptoData(tickers, startDate, resampleFreq);
        return ResponseEntity.ok(historicalCryptoData);
    }
}