package wileyt3.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wileyt3.backend.entity.PortfolioStock;

import java.util.List;

public interface PortfolioStockRepository extends JpaRepository<PortfolioStock, Integer> {
    List<PortfolioStock> findByUser_Id(Integer userId);

    void deleteByUser_Id(Integer userId);

}