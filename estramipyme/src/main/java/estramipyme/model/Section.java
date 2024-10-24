package estramipyme.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "section", schema = "public")
public class Section {
    @Id
    private Long id;
    @Column(nullable = false)
    private String description;

    public Section() {}

    public Section(Long id, String description) {
        this.id = id;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }
}
