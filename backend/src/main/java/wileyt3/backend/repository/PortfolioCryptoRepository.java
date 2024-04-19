package wileyt3.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wileyt3.backend.entity.PortfolioCrypto;

import java.util.List;

public interface PortfolioCryptoRepository extends JpaRepository<PortfolioCrypto, Integer> {
    List<PortfolioCrypto> findByUser_Id(Integer userId);
    void deleteByUser_Id(Integer userId);
}
