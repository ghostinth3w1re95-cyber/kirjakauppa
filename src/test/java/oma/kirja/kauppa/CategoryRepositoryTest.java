package oma.kirja.kauppa;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import oma.kirja.kauppa.domain.Category;
import oma.kirja.kauppa.domain.CategoryRepository;

@DataJpaTest
public class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void testCreateCategory() {
        // Create and save a category
        Category category = new Category("Fiction");
        Category savedCategory = categoryRepository.save(category);

        // Verify the category was saved
        assertThat(savedCategory.getCategoryid()).isNotNull();
        assertThat(savedCategory.getName()).isEqualTo("Fiction");
    }

    @Test
    public void testDeleteCategory() {
        // Create and save a category
        Category category = new Category("Fiction");
        Category savedCategory = categoryRepository.save(category);

        // Delete the category
        categoryRepository.deleteById(savedCategory.getCategoryid());

        // Verify the category was deleted
        assertThat(categoryRepository.findById(savedCategory.getCategoryid())).isEmpty();
    }

    @Test
    public void testFindByNameContainingIgnoreCase() {
        // Create and save categories
        Category category1 = new Category("Fiction");
        Category category2 = new Category("Non-Fiction");
        categoryRepository.save(category1);
        categoryRepository.save(category2);

        // Search for categories containing "fiction" (case insensitive)
        List<Category> categories = categoryRepository.findByNameContainingIgnoreCase("fiction");

        // Verify results
        assertThat(categories).hasSize(2);
        assertThat(categories).extracting(Category::getName).contains("Fiction", "Non-Fiction");
    }
}