package oma.kirja.kauppa;

import oma.kirja.kauppa.domain.Book;
import oma.kirja.kauppa.domain.BookRepository;
import oma.kirja.kauppa.domain.Category;
import oma.kirja.kauppa.domain.CategoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner loadData(BookRepository bookRepository, CategoryRepository categoryRepository) {
        return args -> {
            // Create categories
            Category scifi = new Category("Scifi");
            Category fantasy = new Category("Fantasy");
            Category mystery = new Category("Mystery");
            
            categoryRepository.save(scifi);
            categoryRepository.save(fantasy);
            categoryRepository.save(mystery);

            // Create books with categories
            bookRepository.save(new Book("The Hobbit", "J.R.R. Tolkien", 12.99, fantasy));
            bookRepository.save(new Book("1984", "George Orwell", 14.50, scifi));
            bookRepository.save(new Book("Clean Code", "Robert C. Martin", 29.99, mystery));
            bookRepository.save(new Book("They Can't Hurt Me", "David Goggins", 19.99, mystery));
            bookRepository.save(new Book("Unrestricted", "David Goggins", 18.99, scifi));
        };
    }
}
