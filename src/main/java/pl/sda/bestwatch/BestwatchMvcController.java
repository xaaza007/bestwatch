package pl.sda.bestwatch;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/app/bestwatch/suggestions")
@Controller
public class BestwatchMvcController {


    private SuggestionRepository suggestionRepository;

    public BestwatchMvcController(SuggestionRepository suggestionRepository) {
        this.suggestionRepository = suggestionRepository;
    }

    @GetMapping
    public String allSuggestionsView(Model indexViewModel) {
        indexViewModel.addAttribute("allSuggestions", suggestionRepository.findAll());

        return "index";
    }

    @PostMapping
    public String addSuggestion(Suggestion suggestion) {
        suggestionRepository.save(suggestion);
        return "redirect:/app/bestwatch/suggestions";
    }

    @GetMapping("/form")
    public String form() {
        return "form";
    }
}
