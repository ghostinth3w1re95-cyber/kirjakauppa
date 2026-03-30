package oma.kirja.kauppa;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import oma.kirja.kauppa.domain.Book;
import oma.kirja.kauppa.domain.BookRepository;
import oma.kirja.kauppa.domain.Category;
import oma.kirja.kauppa.domain.CategoryRepository;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "user", authorities = { "USER" })
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void testShowRoot() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    public void testShowIndex() throws Exception {
        mockMvc.perform(get("/index"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    public void testShowBooks() throws Exception {
        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(view().name("books"));
    }

    @Test
    public void testShowAddBook() throws Exception {
        mockMvc.perform(get("/addbook"))
                .andExpect(status().isOk())
                .andExpect(view().name("addbook"));
    }

    @Test
    public void testAddBook() throws Exception {
        Category category = categoryRepository.save(new Category("Test Category For Add"));

        mockMvc.perform(post("/addbook")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("title", "New Book")
                .param("author", "New Author")
                .param("price", "25.00")
                .param("category.categoryid", String.valueOf(category.getCategoryid())))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/books"));
    }

    @Test
    @WithMockUser(username = "admin", authorities = { "ADMIN" })
    public void testDeleteBook() throws Exception {
        Category category = categoryRepository.save(new Category("Test Category For Delete"));
        Book book = bookRepository.save(new Book("Delete Me", "Admin", 15.0, category));

        mockMvc.perform(post("/delete/" + book.getId()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/books"));
    }

    @Test
    public void testShowEditBook() throws Exception {
        Category category = categoryRepository.save(new Category("Test Category For Edit View"));
        Book book = bookRepository.save(new Book("Editable Book", "Editor", 10.0, category));

        mockMvc.perform(get("/edit/" + book.getId()))
                .andExpect(status().isOk())
                .andExpect(view().name("edit"));
    }

    @Test
    public void testEditBook() throws Exception {
        Category category = categoryRepository.save(new Category("Test Category For Edit"));
        Book book = bookRepository.save(new Book("Original Book", "Original Author", 20.0, category));

        mockMvc.perform(post("/edit/" + book.getId())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("title", "Updated Book")
                .param("author", "Updated Author")
                .param("price", "30.00")
                .param("category.categoryid", String.valueOf(category.getCategoryid())))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/books"));
    }

    @Test
    public void testLogin() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }
}
