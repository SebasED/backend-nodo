package estramipyme.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuestionRequestPutDto {
    private Long id;
    private String section;
    private String question;
    private List<String> options;
}
