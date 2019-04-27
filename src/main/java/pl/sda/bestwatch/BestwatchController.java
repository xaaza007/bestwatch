package pl.sda.bestwatch;

import org.springframework.web.bind.annotation.*;
import pl.sda.bestwatch.dto.SuggestionDto;

import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping(BestwatchController.API_BESTWATCH_PATH)
public class BestwatchController {

    static final String API_BESTWATCH_PATH = "/api/bestwatch";
    private SuggestionService suggestionService;

    public BestwatchController(SuggestionService suggestionService) {
        this.suggestionService = suggestionService;
    }

    @GetMapping
    public Collection<SuggestionDto> getAllSuggestions() {
        return suggestionService.findAll();
    }

    @GetMapping(params = "suggestionAuthor")
    public Collection<SuggestionDto> getAuthorSuggestions(@RequestParam String suggestionAuthor) {
        return suggestionService.findAllBySuggestionAuthorNickName(suggestionAuthor);
    }

    @GetMapping(params = "suggestionMovieTitle")
    public Collection<SuggestionDto> getSuggestionsByMovie(@RequestParam String suggestionMovieTitle) {
        return suggestionService.findAllByMovieTitle(suggestionMovieTitle);
    }

    @GetMapping("/{id}")
    public Optional<SuggestionDto> getOneSuggestion(@PathVariable Integer id) {
        return suggestionService.findById(id);
    }

    @PostMapping
    public void addSuggestion(@RequestBody SuggestionDto suggestion) {
        suggestionService.addSuggestion(suggestion);
    }
}
