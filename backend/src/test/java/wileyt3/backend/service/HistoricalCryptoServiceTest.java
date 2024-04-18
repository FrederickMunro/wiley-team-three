package wileyt3.backend.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import wileyt3.backend.dto.HistoricalCryptoDto;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

class HistoricalCryptoServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private HistoricalCryptoService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void fetchHistoricalCryptoData_Successful() {
        // Arrange test query parameters
        String tickers = "btcusd";
        String startDate = "2021-01-01";
        String resampleFreq = "1min";
        String url = "https://api.tiingo.com/tiingo/crypto/prices?tickers=btcusd&startDate=2021-01-01&resampleFreq=1min";

        // Mock response
        HistoricalCryptoDto dto = new HistoricalCryptoDto();
        List<HistoricalCryptoDto> expectedList = Arrays.asList(dto);
        ResponseEntity<List<HistoricalCryptoDto>> responseEntity = ResponseEntity.ok(expectedList);

        when(restTemplate.exchange(
                eq(url),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(new ParameterizedTypeReference<List<HistoricalCryptoDto>>() {
                })
        )).thenReturn(responseEntity);

        // Call method to test
        List<HistoricalCryptoDto> result = service.fetchHistoricalCryptoData(tickers, startDate, resampleFreq);

        // Assert
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(expectedList, result);
    }
}
