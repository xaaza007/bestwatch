package pl.sda.bestwatch;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import pl.sda.bestwatch.dto.SuggestionDto;

import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
class SuggestionApiTest {

    private static final String API_BESTWATCH_PATH = SuggestionController.API_BESTWATCH_PATH;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private SuggestionRepository repository;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void cleanDb() {
        repository.deleteAll();
    }

    @Test
    @DisplayName("Można nawiązać połączenie z API")
    void test01() throws Exception {
        mockMvc.perform(get(API_BESTWATCH_PATH))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json("[]"));
    }

    @Test
    @DisplayName("Wysłanie i odebranie sugestii autora")
    void test02() throws Exception {
        // given
        postSuggestion("title", "goobar", "http://imdb.com/title");

        // when
        mockMvc.perform(get(API_BESTWATCH_PATH))

                // then
                .andExpect(jsonPath("$[0].movieTitle", is("title")))
                .andExpect(jsonPath("$[0].suggestionAuthor", is("goobar")))
                .andExpect(jsonPath("$[0].link", is("http://imdb.com/title")))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Wyszukiwanie sugestii autora")
    void test03() throws Exception {
        // given
        postSuggestion("title0", "goobar", "http://link.com/0");
        postSuggestion("title1", "foobar", "http://link.com/1");
        postSuggestion("title2", "goobar", "http://link.com/2");

        // when
        mockMvc.perform(get(API_BESTWATCH_PATH).param("suggestionAuthor", "goobar"))

                // then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    @DisplayName("Wyszukiwanie sugestii po tytule")
    void test04() throws Exception {
        // given
        postSuggestion("title0", "goobar", "http://link.com/0");
        postSuggestion("title0", "foobar", "http://link.com/1");
        postSuggestion("title1", "goobar", "http://link.com/2");

        // when
        mockMvc.perform(get(API_BESTWATCH_PATH).param("suggestionMovieTitle", "title0"))

                // then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }


    private void postSuggestion(String title, String author, String link) throws Exception {
        // @formatter:off
        String jsonPost = "{" +
                "\"movieTitle\": \"" + title + "\"," +
                "\"suggestionAuthor\": \"" + author + "\"," +
                "\"link\": \"" + link + "\"" +
                "}";
        // @formatter:on
        mockMvc.perform(post(API_BESTWATCH_PATH).content(jsonPost).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @Test
    @DisplayName("Dodawanie wielu sugestii za jednym razem")
    void test05 () throws Exception {
        // given
        SuggestionDto firstSuggestion = new SuggestionDto();
        firstSuggestion.setMovieTitle("movie 0");
        firstSuggestion.setLink("http://link.com/0");
        firstSuggestion.setSuggestionAuthor("author");
        SuggestionDto secondSuggestion = new SuggestionDto();
        secondSuggestion.setMovieTitle("movie 1");
        secondSuggestion.setLink("http://link.com/1");
        secondSuggestion.setSuggestionAuthor("author");
        AddSuggestionsDto addSuggestionsDto = new AddSuggestionsDto(Arrays.asList(firstSuggestion, secondSuggestion));
        String addMultipleSuggestionsJson = objectMapper.writeValueAsString(addSuggestionsDto);

        // when
        mockMvc.perform(
                post(API_BESTWATCH_PATH + "/many")
                .content(addMultipleSuggestionsJson)
                        .contentType(MediaType.APPLICATION_JSON))

        // then
        .andExpect(status().isOk());
        mockMvc.perform(get(API_BESTWATCH_PATH)).andExpect(jsonPath("$", hasSize(2)));
    }

//    @Test
//    @DisplayName("wysyłanie kilku sugestii i odbieranie kilku  sugestii")
//    void test03(
//    ) {
//        ArrayList <String> jsonPosts = new ArrayList<String>();
//        jsonPosts.add("{\"movie\":\"title\"}");
//        jsonPosts.add("{\"movie\":\"title2\"}");
//
//        mockMvc.perform(post(API_BESTWATCH_PATH).content())
//
//    }

}
