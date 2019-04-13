package pl.sda.bestwatch;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class SuggestionAPITest {

    private static final String API_BESTWATCH_PATH = BestwatchController.API_BESTWATCH_PATH;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private SuggestionRepository repository;

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
        // @formatter:off
        String jsonPost = "{" +
                "\"movieTitle\":\"title\"," +
                "\"suggestionAuthor\": \"goobar\"," +
                "\"link\": \"http://imdb.com/title\"" +
             "}";
        // @formatter:on
        mockMvc.perform(post(API_BESTWATCH_PATH).content(jsonPost).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        mockMvc.perform(get(API_BESTWATCH_PATH))
                .andExpect(jsonPath("$[0].movieTitle", is("title")))
                .andExpect(jsonPath("$[0].suggestionAuthor", is("goobar")))
                .andExpect(jsonPath("$[0].link", is("http://imdb.com/title")))
                .andExpect(status().isOk());
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
