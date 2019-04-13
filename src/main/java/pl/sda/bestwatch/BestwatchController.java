package pl.sda.bestwatch;

import org.springframework.web.bind.annotation.*;
import pl.sda.bestwatch.dto.SuggestionDto;
import pl.sda.bestwatch.dto.SuggestionDtoConverter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    public Collection<SuggestionDto> getAllSuggestion() {
        return suggestionRepository.findAll().stream().map(
                SuggestionDtoConverter::fromEntity)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public Optional<Suggestion> getOneSuggestion(@PathVariable Integer id) {
        return suggestionRepository.findById(id);
    }

    @PostMapping
    public void addSuggestion(@RequestBody SuggestionDto suggestion) {
        suggestionRepository.save(SuggestionDtoConverter.toEntity(suggestion));
    }

}
