package pl.sda.bestwatch;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Suggestion {
    @Id
    @GeneratedValue
    private int id;
    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    private Movie movie;
    String content;
    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    private SuggestionAuthor suggestionAuthor;


    public Suggestion() {
    }

    public Suggestion(Movie movie, String content, SuggestionAuthor suggestionAuthor) {
        this.movie = movie;
        this.content = content;
        this.suggestionAuthor = suggestionAuthor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public SuggestionAuthor getSuggestionAuthor() {
        return suggestionAuthor;
    }

    public void setSuggestionAuthor(SuggestionAuthor suggestionAuthor) {
        this.suggestionAuthor = suggestionAuthor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Suggestion that = (Suggestion) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Suggestion{" +
                "content='" + content + '\'' +
                '}';
    }
}
