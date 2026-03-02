package oma.kirja.kauppa.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

@RepositoryRestResource(path = "categories")
public interface CategoryRepository extends JpaRepository<Category, Long> {
    
    @RestResource(path = "by-name", rel = "by-name")
    List<Category> findByNameContainingIgnoreCase(@RequestParam String name);
}
