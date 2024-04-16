package wileyt3.backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "crypto")
public class Crypto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "ticker", nullable = false, length = 10)
    private String ticker;

    @Column(name = "base_currency", length = 10)
    private String baseCurrency;

    @Column(name = "quote_currency", length = 10)
    private String quoteCurrency;

    @Column(name = "last_price")
    private BigDecimal lastPrice;
}
