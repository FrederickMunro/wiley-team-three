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
@Table(name = "portfolio_crypto")
public class PortfolioCrypto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "crypto_id", nullable = false)
    private Crypto crypto;

    @Column(nullable = false)
    private BigDecimal quantityOwned;

    @Column(nullable = false, precision = 16, scale = 2)
    private BigDecimal purchasePrice;

    @Column(name = "purchase_date", nullable = false, columnDefinition = "TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP")
    private Timestamp purchaseDate;

    /**
     * Updates the fields of this PortfolioCrypto object based on another PortfolioCrypto object.
     *
     * @param other The other PortfolioCrypto object from which to copy the properties.
     */
    public void updateFrom(PortfolioCrypto other) {
        this.quantityOwned = other.quantityOwned;
        this.purchasePrice = other.purchasePrice;
        this.purchaseDate = other.purchaseDate;
    }
}
