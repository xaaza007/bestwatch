package pl.sda.bestwatch;

import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import pl.sda.bestwatch.dto.SuggestionDto;
import pl.sda.bestwatch.dto.SuggestionDtoConverter;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SuggestionServiceImpl implements SuggestionService {

    private static final boolean SEND_MAIL = false;
    private SuggestionRepository suggestionRepository;
    private SubscribersRepository subscribersRepository;
    private MailSender mailSender;

    public SuggestionServiceImpl(SuggestionRepository suggestionRepository,
                                 SubscribersRepository subscribersRepository,
                                 MailSender mailSender) {
        this.suggestionRepository = suggestionRepository;
        this.subscribersRepository = subscribersRepository;
        this.mailSender = mailSender;
    }

    @Override
    public Collection<SuggestionDto> findAllBySuggestionAuthorNickName(String nickName) {
/*        Collection<Suggestion> allBySuggestionAuthorNickName =
                suggestionRepository.findAllBySuggestionAuthorNickName(nickName);
        Collection<SuggestionDto> result = new ArrayList<>();
        for (Suggestion sugg: allBySuggestionAuthorNickName) {
            result.add(SuggestionDtoConverter.fromEntity(sugg));
        }
        return result;*/

        return convertSuggestionToDto(suggestionRepository.findAllBySuggestionAuthorNickName(nickName));

    }

    @Override
    public Collection<SuggestionDto> findAll() {

        return convertSuggestionToDto(suggestionRepository.findAll());
    }

    @Override
    public Collection<SuggestionDto> findAllByMovieTitle(String suggestionMovieTitle) {
        return convertSuggestionToDto(suggestionRepository.findAllByMovieTitle(suggestionMovieTitle));
    }

    @Override
    public Optional<SuggestionDto> findById(Integer id) {
        return suggestionRepository.findById(id).map(SuggestionDtoConverter::fromEntity);
    }

    @Override
    public void addSuggestion(SuggestionDto suggestion) {
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

    private Collection<SuggestionDto> convertSuggestionToDto(Collection<Suggestion> collection) {
        return collection.stream()
                .map(SuggestionDtoConverter::fromEntity)
                .collect(Collectors.toList());
    }
}
