package wileyt3.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wileyt3.backend.entity.Stock;

public interface StockRepository extends JpaRepository<Stock, Integer> {
}
