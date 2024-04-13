package wileyt3.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wileyt3.backend.entity.User;

import java.util.Optional;

/**
 * Spring Data JPA repository for the User entity.
 * Provides CRUD operations
 */
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String username);
}
