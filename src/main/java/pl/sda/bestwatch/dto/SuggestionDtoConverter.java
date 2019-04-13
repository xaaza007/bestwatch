package pl.sda.bestwatch.dto;

import pl.sda.bestwatch.Movie;
import pl.sda.bestwatch.Suggestion;
import pl.sda.bestwatch.SuggestionAuthor;

public class SuggestionDtoConverter {
    public static SuggestionDto fromEntity(Suggestion suggestion) {
        SuggestionDto suggestionDto = new SuggestionDto();
        Movie movie = suggestion.getMovie();
        suggestionDto.setMovieTitle(movie.getTitle());
        suggestionDto.setLink(movie.getLinkToMovie());
        suggestionDto.setSuggestionAuthor(suggestion.getSuggestionAuthor().getNickName());
        return suggestionDto;
    }

    public static Suggestion toEntity(SuggestionDto dto) {
        Suggestion suggestion = new Suggestion();
        suggestion.setMovie(new Movie(dto.getMovieTitle(), dto.getLink()));
        suggestion.setSuggestionAuthor(new SuggestionAuthor(dto.getSuggestionAuthor()));
        return suggestion;
    }
}
