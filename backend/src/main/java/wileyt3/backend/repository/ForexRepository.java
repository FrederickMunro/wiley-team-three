package wileyt3.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wileyt3.backend.entity.Forex;

public interface ForexRepository extends JpaRepository<Forex, Integer> {
}