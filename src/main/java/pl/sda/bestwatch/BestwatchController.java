package pl.sda.bestwatch;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping(BestwatchController.API_BESTWATCH_PATH)
public class BestwatchController {

    SuggestionRepository suggestionRepository;
    public static final String API_BESTWATCH_PATH = "/api/bestwatch";

    private Suggestion suggestion;
    ArrayList<Suggestion> suggestions = new ArrayList<>();

    public BestwatchController(SuggestionRepository suggestionRepository) {
        this.suggestionRepository = suggestionRepository;
    }

    @GetMapping
    public Iterable<Suggestion> getAllSuggestion() {
        return suggestionRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Suggestion> getOneSuggestion(@PathVariable Integer id) {
        return suggestionRepository.findById(id);
    }

    @PostMapping
    public void addSuggestion(@RequestBody Suggestion suggestion, Movie movie, SuggestionAuthor suggestionAuthor) {
        suggestionRepository.save(suggestion);
    }

}
