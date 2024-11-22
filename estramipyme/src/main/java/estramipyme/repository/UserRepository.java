package estramipyme.repository;

import org.springframework.stereotype.Repository;
import estramipyme.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {

    boolean existsByEmail(String email);
    boolean existsByDocnumber(String email);
    Optional<Users> findByEmail(String email);
}
