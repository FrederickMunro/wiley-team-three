package wileyt3.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import wileyt3.backend.dto.CryptoApiDto;

import wileyt3.backend.dto.CryptoApiWithPriceDto;
import wileyt3.backend.entity.Crypto;
import wileyt3.backend.repository.CryptoRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
public class CryptoService implements MarketDataService<Crypto> {

    @Value("${tiingo.api.token}")
    private String tiingoToken;

    private final RestTemplate restTemplate;
    private final CryptoRepository cryptoRepository;

    @Autowired
    public CryptoService(RestTemplate restTemplate, CryptoRepository cryptoRepository) {
        this.restTemplate = restTemplate;
        this.cryptoRepository = cryptoRepository;
    }

    @Override
    public Crypto fetchMarketData(String ticker) {
        CryptoApiDto cryptoApiDto = fetchCryptoDataFromTiingo(ticker);
        BigDecimal lastPrice = fetchClosingPriceFromTiingo(ticker);
        return mapToCrypto(cryptoApiDto, lastPrice);
    }

    private CryptoApiDto fetchCryptoDataFromTiingo(String ticker) {
        String url = "https://api.tiingo.com/tiingo/crypto/" + ticker;

        ResponseEntity<CryptoApiDto[]> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                new HttpEntity<>(createTiingoHeaders()),
                CryptoApiDto[].class
        );
        CryptoApiDto[] cryptoApiDtos = response.getBody();
        if (cryptoApiDtos != null && cryptoApiDtos.length > 0) {
            return cryptoApiDtos[0];
        } else {
            return null;
        }

    }

    private BigDecimal fetchClosingPriceFromTiingo(String ticker) {
        String url = "https://api.tiingo.com/tiingo/crypto/prices?tickers=" + ticker.toUpperCase();

        ResponseEntity<CryptoApiWithPriceDto[]> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                new HttpEntity<>(createTiingoHeaders()),
                CryptoApiWithPriceDto[].class
        );
        return response.getBody() != null ? response.getBody()[0].getPriceData().get(0).getClose() : null;

    }


    private HttpHeaders createTiingoHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", tiingoToken);
        return headers;
    }

    private Crypto mapToCrypto(CryptoApiDto cryptoApiDto, BigDecimal lastPrice) {
        Crypto crypto = new Crypto();
        crypto.setTicker(cryptoApiDto.getTicker());
        crypto.setBaseCurrency(cryptoApiDto.getBaseCurrency());
        crypto.setQuoteCurrency(cryptoApiDto.getQuoteCurrency());
        crypto.setLastPrice(lastPrice);
        return crypto;
    }


    @Override
    public Crypto saveMarketData(Crypto data) {
        return cryptoRepository.save(data);
    }

    @Override
    public Crypto findById(Integer id) {
        return cryptoRepository.findById(id).orElse(null);
    }

    @Override
    public Map<String, Crypto> fetchAllMarketData() {
        return null;
    }

    @Override
    public Page<Crypto> findAll(Pageable pageable) {
        return cryptoRepository.findAll(pageable);
    }

    @Override
    public List<Crypto> findAll() {
        return cryptoRepository.findAll();
    }

    /**
     * Updates market data for a given stock by its ID.
     *
     * @param id The ID of the stock to update.
     * @return The updated Stock object.
     */
    @Override
    public Crypto updateMarketData(Integer id) {
        Crypto existingCrypto = findById(id);
        if (existingCrypto == null) {
            throw new IllegalArgumentException("Crypto not found with ID: " + id);
        }
        Crypto updatedData = fetchMarketData(existingCrypto.getTicker());
        existingCrypto.updateFrom(updatedData);
        return cryptoRepository.save(existingCrypto);
    }

    @Override
    public void deleteMarketData(Integer id) {
        cryptoRepository.deleteById(id);
    }

}
