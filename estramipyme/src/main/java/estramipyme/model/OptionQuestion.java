package estramipyme.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "option_question", schema = "public")
public class OptionQuestion {
    @Id
    private Long id;
    @Column(name = "option_id", nullable = false)
    private Long optionId;
    @Column(name = "question_id", nullable = false)
    private Long questionId;

    public OptionQuestion() {}

    public OptionQuestion(Long id, Long optionId, Long questionId) {
        this.id = id;
        this.optionId = optionId;
        this.questionId = questionId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOptionId() {
        return optionId;
    }

    public void setOptionId(Long optionId) {
        this.optionId = optionId;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }
}
