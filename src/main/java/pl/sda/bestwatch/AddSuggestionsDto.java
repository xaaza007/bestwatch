package pl.sda.bestwatch;

import pl.sda.bestwatch.dto.SuggestionDto;

import java.util.Collection;
import java.util.List;

public class AddSuggestionsDto {
    private Collection<SuggestionDto> suggestions;

    public AddSuggestionsDto(List<SuggestionDto> suggestions) {
        this.suggestions = suggestions;
    }

    public AddSuggestionsDto() {
    }

    public void setSuggestions(Collection<SuggestionDto> suggestions) {
        this.suggestions = suggestions;
    }

    public Collection<SuggestionDto> getSuggestions() {
        return suggestions;
    }
}
