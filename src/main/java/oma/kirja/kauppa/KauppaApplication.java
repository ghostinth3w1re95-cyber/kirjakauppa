package oma.kirja.kauppa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import oma.kirja.kauppa.domain.Category;
import oma.kirja.kauppa.domain.CategoryRepository;

@SpringBootApplication
public class KauppaApplication {

	private static final Logger log = LoggerFactory.getLogger(KauppaApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(KauppaApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(CategoryRepository categoryRepository) {
		return (args) -> {
			log.info("Save some sample categories");
			categoryRepository.save(new Category("Scifi"));
			categoryRepository.save(new Category("Comic"));
			categoryRepository.save(new Category("Fantasy"));
			categoryRepository.save(new Category("Mystery"));
			categoryRepository.save(new Category("Romance"));
			
			log.info("Fetch all the categories");
			categoryRepository.findAll().forEach(c -> log.info(c.toString()));
		};
	}

}
