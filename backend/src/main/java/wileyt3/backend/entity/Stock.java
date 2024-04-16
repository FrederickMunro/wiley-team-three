package wileyt3.backend.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
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
@Table(name = "stock")
public class Stock {

    @Id
    @JsonInclude(JsonInclude.Include.NON_NULL)      // exclude the id field from serialization when it's null. Can Also use @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "symbol", nullable = false, length = 10)
    private String symbol;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "exchange", length = 50)
    private String exchange;

    @JsonInclude(JsonInclude.Include.NON_NULL)   // exclude the lastPrice field from serialization when it's null. Can Also use @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Column(name = "last_price", precision = 10)
    private BigDecimal lastPrice;
}
