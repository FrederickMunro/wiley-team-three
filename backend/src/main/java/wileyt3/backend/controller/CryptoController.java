package wileyt3.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wileyt3.backend.entity.Crypto;
import wileyt3.backend.service.CryptoService;

import java.util.List;

@RestController
@RequestMapping("/crypto")
public class CryptoController {

    private final CryptoService cryptoService;

    public CryptoController(CryptoService cryptoService) {
        this.cryptoService = cryptoService;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a crypto by ID", description = "Retrieves crypto data by its ID.")
    public ResponseEntity<Crypto> getCryptoById(@PathVariable Integer id) {
        Crypto crypto = cryptoService.findById(id);
        return crypto != null ? ResponseEntity.ok(crypto) : ResponseEntity.notFound().build();
    }

    @GetMapping("/all")
    @Operation(summary = "Get all cryptos from database", description = "Retrieves all cryptos.")
    public ResponseEntity<List<Crypto>> getAllCryptos() {
        List<Crypto> cryptos = cryptoService.findAll();
        return ResponseEntity.ok(cryptos);
    }

    @GetMapping("/page")
    @Operation(summary = "Get all cryptos paginated", description = "Retrieves all cryptos with pagination.")
    public ResponseEntity<Page<Crypto>> getCryptosPaginated(Pageable pageable) {
        Page<Crypto> page = cryptoService.findAll(pageable);
        return ResponseEntity.ok(page);
    }

    @PostMapping("/")
    @Operation(summary = "Add a crypto to DB", description = "Creates a crypto's data.")
    public ResponseEntity<Crypto> addCrypto(@RequestParam String ticker) {
        try {
            Crypto crypto = cryptoService.fetchMarketData(ticker);
            if (crypto != null) {
                return ResponseEntity.ok(cryptoService.saveMarketData(crypto));
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update crypto data by ID", description = "Updates a crypto's data based on the provided ID.")
    public ResponseEntity<Crypto> updateCrypto(@PathVariable Integer id) {
        try {
            Crypto updatedCrypto = cryptoService.updateMarketData(id);
            return ResponseEntity.ok(updatedCrypto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a crypto", description = "Deletes a crypto from the system based on the provided ID.")
    public ResponseEntity<Void> deleteCrypto(@PathVariable Integer id) {
        try {
            cryptoService.deleteMarketData(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
