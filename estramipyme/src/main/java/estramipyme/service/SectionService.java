package estramipyme.service;

import estramipyme.model.Section;
import estramipyme.repository.SectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SectionService {

    private final SectionRepository sectionRepository;

    @Autowired
    public SectionService(SectionRepository sectionRepository) {
        this.sectionRepository = sectionRepository;
    }

    public List<Section> getAllSections() {
        return this.sectionRepository.findAll();
    }

    public Optional<Section> getSection(Long id){
        return this.sectionRepository.findById(id);
    }

    public Optional<Section> getSectionByDescription(String description){
        return this.sectionRepository.findByDescription(description);
    }

    public boolean sectionExists(String sectionDescription) {
        return this.sectionRepository.existsByDescription(sectionDescription);
    }
}
