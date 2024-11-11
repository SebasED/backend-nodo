package estramipyme.repository;

import estramipyme.model.Section;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SectionRepository extends JpaRepository<Section, Long> {
    boolean existsByDescription(String description);
}
