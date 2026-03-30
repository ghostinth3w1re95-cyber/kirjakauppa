package oma.kirja.kauppa;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import oma.kirja.kauppa.domain.Book;
import oma.kirja.kauppa.domain.BookRepository;
import oma.kirja.kauppa.domain.Category;
import oma.kirja.kauppa.domain.CategoryRepository;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "user", authorities = { "USER" })
public class BookRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void testGetAllBooks() throws Exception {
        mockMvc.perform(get("/rest/books"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"));
    }

    @Test
    public void testGetBookById() throws Exception {
        Category category = categoryRepository.save(new Category("REST Test Category"));
        Book book = bookRepository.save(new Book("REST Test Book", "REST Author", 12.5, category));

        mockMvc.perform(get("/rest/books/" + book.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"));
    }
}
