package oma.kirja.kauppa;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class BookRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetAllBooks() throws Exception {
        mockMvc.perform(get("/rest/books"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"));
    }

    @Test
    public void testGetBookById() throws Exception {
        mockMvc.perform(get("/rest/books/1")) // Assuming book with id 1 exists
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"));
    }
}