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

    public Question() {}

    public Question(Long id, String description, Section section) {
        this.id = id;
        this.description = description;
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
