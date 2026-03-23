package oma.kirja.kauppa;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

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
        mockMvc.perform(post("/deletecategory/1")) // Assuming category with id 1 exists
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/categorylist"));
    }

    @Test
    public void testShowEditCategory() throws Exception {
        mockMvc.perform(get("/editcategory/1")) // Assuming category with id 1 exists
                .andExpect(status().isOk())
                .andExpect(view().name("editcategory"));
    }

    @Test
    public void testEditCategory() throws Exception {
        mockMvc.perform(post("/editcategory/1")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("name", "Updated Category"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/categorylist"));
    }
}