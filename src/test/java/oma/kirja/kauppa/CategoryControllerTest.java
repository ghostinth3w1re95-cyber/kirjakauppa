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

import oma.kirja.kauppa.domain.Category;
import oma.kirja.kauppa.domain.CategoryRepository;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "user", authorities = { "USER" })
public class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void testShowCategories() throws Exception {
        mockMvc.perform(get("/categorylist"))
                .andExpect(status().isOk())
                .andExpect(view().name("categorylist"));
    }

    @Test
    public void testShowAddCategory() throws Exception {
        mockMvc.perform(get("/addcategory"))
                .andExpect(status().isOk())
                .andExpect(view().name("addcategory"));
    }

    @Test
    public void testAddCategory() throws Exception {
        mockMvc.perform(post("/addcategory")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("name", "New Category"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/categorylist"));
    }

    @Test
    public void testDeleteCategory() throws Exception {
        Category category = categoryRepository.save(new Category("Delete Category"));

        mockMvc.perform(post("/deletecategory/" + category.getCategoryid()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/categorylist"));
    }

    @Test
    public void testShowEditCategory() throws Exception {
        Category category = categoryRepository.save(new Category("Editable Category"));

        mockMvc.perform(get("/editcategory/" + category.getCategoryid()))
                .andExpect(status().isOk())
                .andExpect(view().name("editcategory"));
    }

    @Test
    public void testEditCategory() throws Exception {
        Category category = categoryRepository.save(new Category("Original Category"));

        mockMvc.perform(post("/editcategory/" + category.getCategoryid())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("name", "Updated Category"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/categorylist"));
    }
}
