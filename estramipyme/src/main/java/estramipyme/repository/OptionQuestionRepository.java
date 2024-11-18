package estramipyme.repository;

import estramipyme.model.OptionQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OptionQuestionRepository extends JpaRepository<OptionQuestion, Long> {
    List<OptionQuestion> findByQuestionId(Long questionId);
}
