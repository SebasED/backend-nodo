package estramipyme.repository;

import estramipyme.model.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    public Optional<Answer> findByUserId(Long userId);

    public boolean existsByUserId(Long userId);
}
