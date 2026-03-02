package oma.kirja.kauppa.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

@RepositoryRestResource(path = "books")
public interface BookRepository extends JpaRepository<Book, Long> {
    
    @RestResource(path = "by-title", rel = "by-title")
    List<Book> findByTitleContainingIgnoreCase(@RequestParam String title);
    
    @RestResource(path = "by-author", rel = "by-author")
    List<Book> findByAuthorContainingIgnoreCase(@RequestParam String author);
    
    @RestResource(path = "by-price-max", rel = "by-price-max")
    List<Book> findByPriceLessThanEqual(@RequestParam Double price);
    
    @RestResource(path = "by-category", rel = "by-category")
    List<Book> findByCategory_Categoryid(@RequestParam Long categoryid);
}
