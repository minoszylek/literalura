package com.aluracursos.literalura;

import com.aluracursos.literalura.services.ToolKit;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner {
	public static void main(String[] args) {
		SpringApplication.run(LiteraluraApplication.class, args);
	}

    @Override
	public void run(String... args) throws Exception {
		System.out.println("¡Bienvenido a LiterAlura!");
		ToolKit toolKit = new ToolKit();

		while (toolKit.getOption() != 0) {

			toolKit.menu(toolKit.getUserMenuOptions(), toolKit.getOptions());

			switch (toolKit.getOption()) {

				case 1: {
					toolKit.searchBookForTitle();
					break;
				}
				case 2: {
					System.out.println("Listar libros!");
					break;
				}
			}
		}
		System.out.println("¡Gracias por usar nuestra aplicación!");

	}
}

