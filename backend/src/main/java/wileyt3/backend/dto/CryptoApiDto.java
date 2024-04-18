package wileyt3.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class CryptoApiDto {
    @JsonProperty("ticker")
    private String ticker;
    @JsonProperty("baseCurrency")
    private String baseCurrency;
    @JsonProperty("quoteCurrency")
    private String quoteCurrency;
    @JsonProperty("priceData")
    private List<PriceDataDto> priceData;

    @Data
    @NoArgsConstructor
    public static class PriceDataDto {
        @JsonProperty("date")
        private OffsetDateTime date;
        @JsonProperty("close")
        private BigDecimal close;
    }

}
