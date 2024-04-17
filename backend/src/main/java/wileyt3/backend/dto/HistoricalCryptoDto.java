package wileyt3.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class HistoricalCryptoDto {

    @JsonProperty("ticker")
    private String tickers;

    @JsonProperty("exchanges")
    private String exchanges;

    @JsonProperty("startDate")
    private LocalDateTime startDate;

    @JsonProperty("endDate")
    private LocalDateTime endDate;

    private List<String> attributes;

    @JsonProperty("class")
    private String classType;
}
