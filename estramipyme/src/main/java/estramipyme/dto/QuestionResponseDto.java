package estramipyme.dto;

import java.util.List;

public class QuestionResponseDto {
    private Long id;
    private String section;
    private String question;
    private List<String> options;

    public QuestionResponseDto() {}

    public QuestionResponseDto(Long id, String section, String question, List<String> options) {
        this.id = id;
        this.section = section;
        this.question = question;
        this.options = options;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }
}
