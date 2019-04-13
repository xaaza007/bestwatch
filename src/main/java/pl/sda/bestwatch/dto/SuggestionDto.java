package pl.sda.bestwatch.dto;

import lombok.Data;

@Data
public class SuggestionDto {
    private String movieTitle;
    private String suggestionAuthor;
    private String link;
}
