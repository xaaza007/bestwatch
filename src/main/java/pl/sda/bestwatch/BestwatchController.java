package pl.sda.bestwatch;

import org.springframework.web.bind.annotation.*;
import pl.sda.bestwatch.dto.SuggestionDto;
import pl.sda.bestwatch.dto.SuggestionDtoConverter;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(BestwatchController.API_BESTWATCH_PATH)
public class BestwatchController {

    SuggestionRepository suggestionRepository;
    public static final String API_BESTWATCH_PATH = "/api/bestwatch";

    public BestwatchController(SuggestionRepository suggestionRepository) {
        this.suggestionRepository = suggestionRepository;
    }

    @GetMapping
    public Collection<SuggestionDto> getAllSuggestions() {
        return convertToDto(suggestionRepository.findAll());
    }

    @GetMapping(params = "suggestionAuthor")
    public Collection<SuggestionDto> getAuthorSuggestions(@RequestParam String suggestionAuthor) {
        return convertToDto(suggestionRepository.findAllBySuggestionAuthorNickName(suggestionAuthor));
    }

    @GetMapping("/{id}")
    public Optional<SuggestionDto> getOneSuggestion(@PathVariable Integer id) {
        return suggestionRepository.findById(id).map(SuggestionDtoConverter::fromEntity);
    }

    @PostMapping
    public void addSuggestion(@RequestBody SuggestionDto suggestion) {
        suggestionRepository.save(SuggestionDtoConverter.toEntity(suggestion));
    }

    private static Collection<SuggestionDto> convertToDto(final Collection<Suggestion> suggestions) {
        return suggestions.stream().map(SuggestionDtoConverter::fromEntity).collect(Collectors.toList());
    }
}
