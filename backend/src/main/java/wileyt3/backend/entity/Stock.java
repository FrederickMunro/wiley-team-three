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

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "stock")
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "symbol", nullable = false, length = 10)
    private String symbol;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "exchange", length = 50)
    private String exchange;

    @Column(name = "last_price", precision = 10)
    private Double lastPrice;
}
