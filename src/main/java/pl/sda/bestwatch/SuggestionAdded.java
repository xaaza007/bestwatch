package pl.sda.bestwatch;

import java.util.Objects;

public class SuggestionAdded {
    private Suggestion suggestion;

    public SuggestionAdded(Suggestion suggestion) {
        this.suggestion = suggestion;
    }

    public Suggestion getSuggestion() {
        return suggestion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SuggestionAdded that = (SuggestionAdded) o;
        return Objects.equals(suggestion, that.suggestion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(suggestion);
    }
}
