package oma.kirja.kauppa;

import oma.kirja.kauppa.domain.Book;
import oma.kirja.kauppa.domain.BookRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner loadData(BookRepository bookRepository) {
        return args -> {
            bookRepository.save(new Book("The Hobbit", "J.R.R. Tolkien", 12.99));
            bookRepository.save(new Book("1984", "George Orwell", 14.50));
            bookRepository.save(new Book("Clean Code", "Robert C. Martin", 29.99));
            bookRepository.save(new Book("They Can't Hurt Me", "David Goggins", 19.99));
            bookRepository.save(new Book("Unrestricted", "David Goggins", 18.99));
        };
    }
}
