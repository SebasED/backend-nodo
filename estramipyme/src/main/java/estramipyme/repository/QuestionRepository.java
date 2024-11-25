package estramipyme.repository;

import estramipyme.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    boolean existsByDescription(String description);
    List<Question> findBySection_Id(Long sectionId);
}
