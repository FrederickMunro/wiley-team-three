package wileyt3.backend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class CryptoApiDto {
    private String ticker;
    private String baseCurrency;
    private String quoteCurrency;
    private String name;
}
