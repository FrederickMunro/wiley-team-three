package wileyt3.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "portfolio_forex")
public class PortfolioForex {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "forex_id", nullable = false)
    private Forex forex;

    @Column(nullable = false)
    private BigDecimal quantityOwned;

    @Column(nullable = false, precision = 10, scale = 6)
    private BigDecimal purchaseRate;

    @Column(name = "purchase_date", nullable = false, columnDefinition = "TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP")
    private Timestamp purchaseDate;

}
