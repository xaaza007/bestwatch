package pl.sda.bestwatch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BestwatchMvcController {


    private SuggestionRepository suggestionRepository;

    public BestwatchMvcController(SuggestionRepository suggestionRepository) {
        this.suggestionRepository = suggestionRepository;
    }

    @GetMapping("/app/bestwatch/suggestions")
    public String allSuggestionsView(Model indexViewModel) {
        indexViewModel.addAttribute("allSuggestions", suggestionRepository.findAll());

        return "index";
    }
}
