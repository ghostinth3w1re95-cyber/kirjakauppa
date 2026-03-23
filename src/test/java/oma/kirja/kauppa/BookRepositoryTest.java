package oma.kirja.kauppa;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import oma.kirja.kauppa.domain.Book;
import oma.kirja.kauppa.domain.BookRepository;
import oma.kirja.kauppa.domain.Category;
import oma.kirja.kauppa.domain.CategoryRepository;

@DataJpaTest
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void testCreateBook() {
        // Create and save a category first
        Category category = new Category("Fiction");
        categoryRepository.save(category);

        // Create and save a book
        Book book = new Book("Test Book", "Test Author", 19.99, category);
        Book savedBook = bookRepository.save(book);

        // Verify the book was saved
        assertThat(savedBook.getId()).isNotNull();
        assertThat(savedBook.getTitle()).isEqualTo("Test Book");
        assertThat(savedBook.getAuthor()).isEqualTo("Test Author");
        assertThat(savedBook.getPrice()).isEqualTo(19.99);
        assertThat(savedBook.getCategory()).isEqualTo(category);
    }

    @Test
    public void testDeleteBook() {
        // Create and save a category first
        Category category = new Category("Fiction");
        categoryRepository.save(category);

        // Create and save a book
        Book book = new Book("Test Book", "Test Author", 19.99, category);
        Book savedBook = bookRepository.save(book);

        // Delete the book
        bookRepository.deleteById(savedBook.getId());

        // Verify the book was deleted
        assertThat(bookRepository.findById(savedBook.getId())).isEmpty();
    }

    @Test
    public void testFindByTitleContainingIgnoreCase() {
        // Create and save a category first
        Category category = new Category("Fiction");
        categoryRepository.save(category);

        // Create and save books
        Book book1 = new Book("Java Programming", "John Doe", 29.99, category);
        Book book2 = new Book("Python Guide", "Jane Smith", 24.99, category);
        bookRepository.save(book1);
        bookRepository.save(book2);

        // Search for books containing "java" (case insensitive)
        List<Book> books = bookRepository.findByTitleContainingIgnoreCase("java");

        // Verify results
        assertThat(books).hasSize(1);
        assertThat(books.get(0).getTitle()).isEqualTo("Java Programming");
    }

    @Test
    public void testFindByAuthorContainingIgnoreCase() {
        // Create and save a category first
        Category category = new Category("Fiction");
        categoryRepository.save(category);

        // Create and save books
        Book book1 = new Book("Book1", "Author One", 19.99, category);
        Book book2 = new Book("Book2", "Author Two", 24.99, category);
        bookRepository.save(book1);
        bookRepository.save(book2);

        // Search for books by author containing "one" (case insensitive)
        List<Book> books = bookRepository.findByAuthorContainingIgnoreCase("one");

        // Verify results
        assertThat(books).hasSize(1);
        assertThat(books.get(0).getAuthor()).isEqualTo("Author One");
    }

    @Test
    public void testFindByPriceLessThanEqual() {
        // Create and save a category first
        Category category = new Category("Fiction");
        categoryRepository.save(category);

        // Create and save books
        Book book1 = new Book("Cheap Book", "Author1", 10.00, category);
        Book book2 = new Book("Expensive Book", "Author2", 50.00, category);
        bookRepository.save(book1);
        bookRepository.save(book2);

        // Search for books with price <= 20.00
        List<Book> books = bookRepository.findByPriceLessThanEqual(20.00);

        // Verify results
        assertThat(books).hasSize(1);
        assertThat(books.get(0).getTitle()).isEqualTo("Cheap Book");
    }
}