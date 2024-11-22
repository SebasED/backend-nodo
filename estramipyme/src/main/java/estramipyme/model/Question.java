package estramipyme.model;

import jakarta.persistence.*;

@Entity
@Table(name = "question", schema = "public")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String description;
    @ManyToOne
    @JoinColumn(referencedColumnName = "id", name = "section_id")
    private Section section;
    private String options;

    public Question() {}

    public Question(Long id, String description, Section section, String options) {
        this.id = id;
        this.description = description;
        this.section = section;
        this.options = options;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public Section getSection() {
        return section;
    }
}
