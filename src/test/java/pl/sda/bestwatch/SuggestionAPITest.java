package pl.sda.bestwatch;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
public class SuggestionAPITest {

    private static final String API_BESTWATCH_PATH = BestwatchController.API_BESTWATCH_PATH;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Można nawiązać połączenie z API")
    void test01() throws Exception {
        mockMvc.perform(get(API_BESTWATCH_PATH))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json("[]"));
    }

    @Test
    @DisplayName("Wysłanie i odebranie sugestii")
    void test02() throws Exception {
        String jsonPost = "{\"movie\":\"title\"}";
        mockMvc.perform(post(API_BESTWATCH_PATH).content(jsonPost).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        mockMvc.perform(get(API_BESTWATCH_PATH)).andExpect(jsonPath("$[0].movie", is("title")))
                .andExpect(status().isOk());
    }
}
