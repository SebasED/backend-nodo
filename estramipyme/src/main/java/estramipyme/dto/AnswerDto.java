package estramipyme.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.vladmihalcea.hibernate.type.json.JsonType;
import jakarta.persistence.Column;
import org.hibernate.annotations.Type;

public class AnswerDto {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Type(JsonType.class)
    @Column(name = "answers", columnDefinition = "jsonb", nullable = false)
    private JsonNode answers;

    public AnswerDto() {
    }

    public AnswerDto(Long userId, JsonNode answers) {
        this.userId = userId;
        this.answers = answers;
    }

    public AnswerDto(Long id, Long userId, JsonNode answers) {
        this.id = id;
        this.userId = userId;
        this.answers = answers;
    }

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public JsonNode getAnswers() {
        return answers;
    }

    public void setAnswers(JsonNode answers) {
        this.answers = answers;
    }
}
