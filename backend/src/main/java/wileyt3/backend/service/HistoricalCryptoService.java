package wileyt3.backend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import wileyt3.backend.dto.HistoricalCryptoDto;
import wileyt3.backend.repository.CryptoRepository;

import java.util.List;

@Service
public class HistoricalCryptoService {

    @Value("${tiingo.api.token}")
    private String tiingoToken;

    private final String tiingoBaseUrl = "https://api.tiingo.com/tiingo/crypto/prices";
    private final RestTemplate restTemplate;

//    private CryptoRepository cryptoRepository;

    public HistoricalCryptoService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public List<HistoricalCryptoDto> fetchHistoricalCryptoData(String tickers, String startDate, String resampleFreq) {
        // Construct the URL with parameters
        String url = tiingoBaseUrl + "?tickers=" + tickers + "&startDate=" + startDate + "&resampleFreq=" + resampleFreq;

        // Set up headers with Tiingo token
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", tiingoToken);

        // Make the HTTP request
        ResponseEntity<List<HistoricalCryptoDto>> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                new ParameterizedTypeReference<List<HistoricalCryptoDto>>() {});

        // Extract and return the response body
        List<HistoricalCryptoDto> historicalCryptoDtos = responseEntity.getBody();

        if (historicalCryptoDtos != null && !historicalCryptoDtos.isEmpty()) {
            return historicalCryptoDtos;
        } else {
            throw new RuntimeException("Failed to fetch historical crypto data from Tiingo.");
        }
    }

}

