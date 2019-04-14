package pl.sda.bestwatch;

import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.*;
import pl.sda.bestwatch.dto.SuggestionDto;
import pl.sda.bestwatch.dto.SuggestionDtoConverter;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(BestwatchController.API_BESTWATCH_PATH)
public class BestwatchController {

    public static final String API_BESTWATCH_PATH = "/api/bestwatch";
    private static final boolean SEND_MAIL = false;

    private SuggestionRepository suggestionRepository;
    private SubscribersRepository subscribersRepository;
    private MailSender mailSender;

    public BestwatchController(SuggestionRepository suggestionRepository,
                               SubscribersRepository subscribersRepository, MailSender mailSender) {
        this.suggestionRepository = suggestionRepository;
        this.subscribersRepository = subscribersRepository;
        this.mailSender = mailSender;
    }

    @GetMapping
    public Collection<SuggestionDto> getAllSuggestions() {
        return convertToDto(suggestionRepository.findAll());
    }

    @GetMapping(params = "suggestionAuthor")
    public Collection<SuggestionDto> getAuthorSuggestions(@RequestParam String suggestionAuthor) {
        return convertToDto(suggestionRepository.findAllBySuggestionAuthorNickName(suggestionAuthor));
    }

    @GetMapping(params = "suggestionMovieTitle")
    public Collection<SuggestionDto> getSuggestionsByMovie(@RequestParam String suggestionMovieTitle) {
        return convertToDto(suggestionRepository.findAllByMovieTitle(suggestionMovieTitle));
    }

    @GetMapping("/{id}")
    public Optional<SuggestionDto> getOneSuggestion(@PathVariable Integer id) {
        return suggestionRepository.findById(id).map(SuggestionDtoConverter::fromEntity);
    }

    @PostMapping
    public void addSuggestion(@RequestBody SuggestionDto suggestion) {
        suggestionRepository.save(SuggestionDtoConverter.toEntity(suggestion));
        if (SEND_MAIL)
            subscribersRepository.findAll().forEach(subscriber -> sendMail(subscriber, suggestion));
    }

    private void sendMail(Subscriber subscriber, SuggestionDto suggestion) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("Suggestion");
        message.setTo(subscriber.getEmailAddress());
        message.setText(suggestion.getMovieTitle() + ", " + suggestion.getLink() + ", "
                + suggestion.getSuggestionAuthor());
        mailSender.send(message);
    }

    private static Collection<SuggestionDto> convertToDto(final Collection<Suggestion> suggestions) {
        return suggestions.stream().map(SuggestionDtoConverter::fromEntity).collect(Collectors.toList());
    }
}
