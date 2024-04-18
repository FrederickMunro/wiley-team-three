package wileyt3.backend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import wileyt3.backend.dto.HistoricalCryptoDto;

import java.util.List;

@Service
public class HistoricalCryptoService {

    @Value("${tiingo.api.token}")
    private String tiingoToken;

    private final String tiingoBaseUrl = "https://api.tiingo.com/tiingo/crypto/prices";
    private final RestTemplate restTemplate;

    public HistoricalCryptoService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public List<HistoricalCryptoDto> fetchHistoricalCryptoData(String tickers, String startDate, String resampleFreq) {
        String url = buildUrl(tickers, startDate, resampleFreq);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", tiingoToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<List<HistoricalCryptoDto>> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<List<HistoricalCryptoDto>>() {
                });

        if (responseEntity.getBody() != null && !responseEntity.getBody().isEmpty()) {
            return responseEntity.getBody();
        } else {
            throw new RuntimeException("No historical data available for the specified parameters.");
        }
    }

    private String buildUrl(String tickers, String startDate, String resampleFreq) {
        return UriComponentsBuilder
                .fromHttpUrl(tiingoBaseUrl)
                .queryParam("tickers", tickers)
                .queryParam("startDate", startDate)
                .queryParam("resampleFreq", resampleFreq)
                .toUriString();
    }

}

