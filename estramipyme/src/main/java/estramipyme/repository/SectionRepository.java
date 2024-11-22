package estramipyme.repository;

import estramipyme.model.Section;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface SectionRepository extends JpaRepository<Section, Long> {
    boolean existsByDescription(String description);

    Optional<Section> findByDescription(String description);
}
