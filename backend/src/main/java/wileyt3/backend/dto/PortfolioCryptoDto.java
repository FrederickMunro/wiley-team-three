package wileyt3.backend.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class PortfolioCryptoDto {
    private Integer id;
    private Integer userId;
    private String ticker;
    private BigDecimal quantity;
    private BigDecimal purchasePrice;
}
