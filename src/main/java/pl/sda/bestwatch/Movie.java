package pl.sda.bestwatch;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Movie {

    @Id
    @GeneratedValue
    int id;
    private String title;
    private String linkToMovie;


    public Movie() {
    }

    public Movie(String title, String linkToMovie) {
        this.title = title;
        this.linkToMovie = linkToMovie;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLinkToMovie() {
        return linkToMovie;
    }

    public void setLinkToMovie(String linkToMovie) {
        this.linkToMovie = linkToMovie;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return id == movie.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
