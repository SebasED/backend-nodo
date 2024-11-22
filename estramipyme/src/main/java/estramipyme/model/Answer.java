package estramipyme.model;

import com.fasterxml.jackson.databind.JsonNode;
import com.vladmihalcea.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "answer")
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relación Uno a Uno (OneToOne) con la tabla User
    @OneToOne
    @JoinColumn(referencedColumnName = "id", name = "user_id", nullable = false, unique = true)
    private Users user;

    @NotNull
    @Type(JsonType.class)
    @Column(name = "answers", columnDefinition = "jsonb")
    private JsonNode answers;

    public Answer() {
    }

    public Answer(Users user, JsonNode answers) {
        this.user = user;
        this.answers = answers;
    }

    public Answer(Long id, Users user, JsonNode answers) {
        this.id = id;
        this.user = user;
        this.answers = answers;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public JsonNode getAnswers() {
        return answers;
    }

    public void setAnswers(JsonNode answers) {
        this.answers = answers;
    }
}