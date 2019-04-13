package pl.sda.bestwatch;

import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Collection;

public interface SuggestionRepository extends JpaRepository<Suggestion, Integer> {
    // @Query("SELECT sug FROM Suggestion sug WHERE sug.suggestionAuthor.nickName = :author")
    Collection<Suggestion> findAllBySuggestionAuthorNickName(String author);
    Collection<Suggestion> findAllByMovieTitle(String movie);
}
