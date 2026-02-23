package oma.kirja.kauppa.web;

import oma.kirja.kauppa.domain.Category;
import oma.kirja.kauppa.domain.CategoryRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class CategoryController {

    private final CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/categorylist")
    public String showCategories(Model model) {
        model.addAttribute("categories", categoryRepository.findAll());
        return "categorylist";
    }

    @GetMapping("/addcategory")
    public String showAddCategory(Model model) {
        model.addAttribute("category", new Category());
        return "addcategory";
    }

    @PostMapping("/addcategory")
    public String addCategory(@ModelAttribute Category category) {
        categoryRepository.save(category);
        return "redirect:/categorylist";
    }

    @PostMapping("/deletecategory/{id}")
    public String deleteCategory(@PathVariable Long id) {
        categoryRepository.deleteById(id);
        return "redirect:/categorylist";
    }

    @GetMapping("/editcategory/{id}")
    public String showEditCategory(@PathVariable Long id, Model model) {
        Category category = categoryRepository.findById(id).orElse(null);
        if (category != null) {
            model.addAttribute("category", category);
            return "editcategory";
        }
        return "redirect:/categorylist";
    }

    @PostMapping("/editcategory/{id}")
    public String editCategory(@PathVariable Long id, @ModelAttribute Category category) {
        category.setCategoryid(id);
        categoryRepository.save(category);
        return "redirect:/categorylist";
    }
}
