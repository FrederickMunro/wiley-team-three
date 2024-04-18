package wileyt3.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class HistoricalCryptoDto {

    @JsonProperty("ticker")
    private String ticker;

    @JsonProperty("baseCurrency")
    private String baseCurrency;

    @JsonProperty("quoteCurrency")
    private String quoteCurrency;

    @JsonProperty("priceData")
    private List<PriceData> priceData;

    @Data
    @NoArgsConstructor
    public static class PriceData {
        @JsonProperty("date")
        private OffsetDateTime date;
        @JsonProperty("close")
        private Double close;
    }
}
