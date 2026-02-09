package oma.kirja.kauppa.web;

import oma.kirja.kauppa.domain.BookRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BookController {

    private final BookRepository bookRepository;

    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping("/")
    public String showRoot(Model model) {
        model.addAttribute("message", "Welcome to the Bookstore!");
        return "index";
    }

    @GetMapping("/index")
    public String showIndex(Model model) {
        model.addAttribute("message", "Welcome to the Bookstore!");
        return "index";
    }

    @GetMapping("/books")
    public String showBooks(Model model) {
        model.addAttribute("books", bookRepository.findAll());
        return "books";
    }
}

