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

@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

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
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testAddBook() throws Exception {
        mockMvc.perform(post("/addbook")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("title", "New Book")
                .param("author", "New Author")
                .param("price", "25.00")
                .param("category.categoryid", "1")) // Assuming category exists
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/books"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testDeleteBook() throws Exception {
        mockMvc.perform(post("/delete/1")) // Assuming book with id 1 exists
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/books"));
    }

    @Test
    public void testShowEditBook() throws Exception {
        mockMvc.perform(get("/edit/1")) // Assuming book with id 1 exists
                .andExpect(status().isOk())
                .andExpect(view().name("edit"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testEditBook() throws Exception {
        mockMvc.perform(post("/edit/1")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("title", "Updated Book")
                .param("author", "Updated Author")
                .param("price", "30.00")
                .param("category.categoryid", "1"))
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