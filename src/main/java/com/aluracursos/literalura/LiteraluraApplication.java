package com.aluracursos.literalura;

import com.aluracursos.literalura.services.ToolKit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.aluracursos.literalura")
public class LiteraluraApplication implements CommandLineRunner {

	@Autowired
	private ToolKit toolKit;
	public static void main(String[] args) {
		SpringApplication.run(LiteraluraApplication.class, args);
	}

    @Override
	public void run(String... args) throws Exception {
		System.out.println("\n¡Bienvenido a LiterAlura!");
		while (toolKit.getOption() != 0) {

			toolKit.menu(toolKit.getUserMenuOptions(), toolKit.getOptions());

			switch (toolKit.getOption()) {

				case 1: {
					toolKit.saveBook(toolKit.searchBookForTitle());
					break;
				}
				case 2: {
					toolKit.searchBooks();
					break;
				}
				case 3: {
					toolKit.searchAuthors();
					break;
				}
				case 4: {
					toolKit.livingAuthorsByYear();
					break;
				}
				case 5: {
					toolKit.searchBooksByLanguage();
					break;
				}
			}
		}
		System.out.println("Cerrando aplicación...");

	}
}

