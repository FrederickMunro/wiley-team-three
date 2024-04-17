package wileyt3.backend.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PortfolioStockDto {
    private Integer userId;
    private String symbol;
    private Integer quantity;
    private BigDecimal purchasePrice;
}
