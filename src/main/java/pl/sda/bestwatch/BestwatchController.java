package pl.sda.bestwatch;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping(BestwatchController.API_BESTWATCH_PATH)
public class BestwatchController {

    public static final String API_BESTWATCH_PATH = "/api/bestwatch";

    private Suggestion suggestion;

    @GetMapping
    public Iterable<Suggestion> get() {
        ArrayList<Suggestion> suggestions = new ArrayList<>();
        if (suggestion != null)
            suggestions.add(suggestion);
        return suggestions;
    }

    @PostMapping
    public void add(@RequestBody Suggestion suggestion) {
        this.suggestion = suggestion;
    }

}
