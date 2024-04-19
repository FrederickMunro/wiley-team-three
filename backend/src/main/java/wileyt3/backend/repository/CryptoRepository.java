package wileyt3.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wileyt3.backend.entity.Crypto;

import java.util.Optional;

public interface CryptoRepository extends JpaRepository<Crypto, Integer> {

    Optional<Crypto> findByTicker(String ticker);
}
