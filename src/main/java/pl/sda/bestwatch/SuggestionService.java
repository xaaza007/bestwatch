package pl.sda.bestwatch;

import pl.sda.bestwatch.dto.SuggestionDto;

import java.util.Collection;
import java.util.Optional;

public interface SuggestionService {
    Collection<SuggestionDto> findAllBySuggestionAuthorNickName (String nickName);
    Collection<SuggestionDto> findAll ();
    Collection<SuggestionDto> findAllByMovieTitle(String suggestionMovieTitle);
    Optional<SuggestionDto> findById(Integer id);
    void addSuggestion(SuggestionDto suggestion);
    void addManySuggestions (AddSuggestionsDto addSuggestionsDto);

}
