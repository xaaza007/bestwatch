package pl.sda.bestwatch;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import pl.sda.bestwatch.dto.SuggestionDto;
import pl.sda.bestwatch.dto.SuggestionDtoConverter;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SuggestionServiceImpl implements SuggestionService {

    private SuggestionRepository suggestionRepository;
    private ApplicationEventPublisher eventPublisher;

    public SuggestionServiceImpl(SuggestionRepository suggestionRepository,
                                 ApplicationEventPublisher eventPublisher) {
        this.suggestionRepository = suggestionRepository;
        this.eventPublisher = eventPublisher;
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
        Suggestion addedSuggestion = suggestionRepository.save(SuggestionDtoConverter.toEntity(suggestion));
        eventPublisher.publishEvent(new SuggestionAdded(addedSuggestion));
    }

    @Override
    @Transactional
    public void addManySuggestions(AddSuggestionsDto addSuggestionsDto) {
        for (SuggestionDto suggestionDto : addSuggestionsDto.getSuggestions()) {
            addSuggestion(suggestionDto);
        }
    }

    private Collection<SuggestionDto> convertSuggestionToDto(Collection<Suggestion> collection) {
        return collection.stream()
                .map(SuggestionDtoConverter::fromEntity)
                .collect(Collectors.toList());
    }
}
