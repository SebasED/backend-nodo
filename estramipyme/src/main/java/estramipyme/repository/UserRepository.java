package estramipyme.repository;

import org.springframework.stereotype.Repository;
import estramipyme.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);
    boolean existsByDocnumber(String email);
    Optional<User> findByEmail(String email);
}
