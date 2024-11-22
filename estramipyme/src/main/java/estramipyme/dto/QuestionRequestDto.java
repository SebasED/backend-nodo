package estramipyme.dto;

import java.util.List;

public class QuestionRequestDto {
    private String section;
    private String question;
    private List<String> options;

    public QuestionRequestDto(String section, String question, List<String> options) {
        this.section = section;
        this.question = question;
        this.options = options;
    }

    public QuestionRequestDto() {
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
