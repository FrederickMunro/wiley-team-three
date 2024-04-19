package wileyt3.backend.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
public class PortfolioCryptoDto {
    private Integer id;
    private Integer userId;
    private String ticker;
    private BigDecimal quantity;
    private BigDecimal purchasePrice;
    private Timestamp purchaseDate;
}
