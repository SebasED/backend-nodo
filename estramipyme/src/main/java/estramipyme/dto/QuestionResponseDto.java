package estramipyme.dto;

import java.util.List;

public class QuestionResponseDto {
    private String id;
    private String section;
    private String question;
    private List<String> options;

    public QuestionResponseDto() {}

    public QuestionResponseDto(String id, String section, String question, List<String> options) {
        this.id = id;
        this.section = section;
        this.question = question;
        this.options = options;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
