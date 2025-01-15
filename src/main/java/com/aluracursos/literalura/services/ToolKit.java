package com.aluracursos.literalura.services;

import com.aluracursos.literalura.models.Author;
import com.aluracursos.literalura.models.Book;
import com.aluracursos.literalura.models.Query;
import com.aluracursos.literalura.repository.AuthorRepository;
import com.aluracursos.literalura.repository.BookRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.*;

@Service
public class ToolKit {
    private final List<Byte> options = List.of((byte) 1, (byte) 2, (byte) 3, (byte) 4, (byte) 5, (byte) 0);
    private final List<Byte> optionsLanguage = List.of((byte) 1, (byte) 2, (byte) 3, (byte) 4, (byte) 5);
    private final Scanner scanner = new Scanner(System.in);
    private Byte userInputOption = 9;
    private Byte option = 9;
    private Short year = 0;
    private String search = "";
    private final CallToAPI callToAPI = new CallToAPI();
    private final Deserialization deserialization = new Deserialization();
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;

    public ToolKit() {
    }

    public void menu(String menu, List<Byte> options) {

        while (option != 0) {
            System.out.println(menu);
            try {
                this.userInputOption = scanner.nextByte();
                scanner.nextLine();
                if (userInputOption == 0) {
                    this.option = userInputOption;
                    break;
                }
                if (options.contains(userInputOption)) {
                    this.option = userInputOption;
                    break;
                } else {
                    System.out.println("'" + userInputOption + "'" + " no es una opción válida.\n¡Debes ingresar un número de las opciones del menú!");
                    menu(getUserMenuOptions(), getOptions());
                    break;
                }
            } catch (InputMismatchException e) {
                String invalidInput = scanner.nextLine();
                System.out.println("'"+ invalidInput +"'" +" no es una opción válida.\n¡Solo debes ingresar los números del menú!");
            }
        }
    }

    public Book searchBookForTitle() throws IOException, InterruptedException {
        Book bookReturn = null;
        System.out.println("Ingresa el nombre del libro que deseas buscar: ");
        this.search = scanner.nextLine();
        var query = deserialization.deserialzationData(callToAPI.call(search.replace(" ", "+")), Query.class);
        System.out.println(query);
        Optional<Book> equalBook = query.books()
                .stream()
                .filter(b -> b.title().equalsIgnoreCase(search))
                .map(Book::new)
                .findFirst();

        if (equalBook.isEmpty()) {
            Optional<Book> book = query.books()
                    .stream()
                    .filter(b -> b.title().toLowerCase().contains(search.toLowerCase()))
                    .map(Book::new)
                    .findFirst();

            if (book.isEmpty()) {
                System.out.println("No se encontró ningún libro con el nombre: " + search);
                return null;
            }
            System.out.println("Libro que contiene el nombre: " + book.get());
            bookReturn = book.get();
            return bookReturn;
        }
        System.out.println("Libro preciso: " + equalBook.get());
        bookReturn = equalBook.get();
        return bookReturn;
    }

    @Transactional
    public void saveBook(Book book) {
        try {
            var authorSerach = authorRepository.findByName(book.getAuthor().getName());
            if (authorSerach.isEmpty()) {
                book.getAuthor().setBook(book);
                authorRepository.save(book.getAuthor());
                System.out.println("\nAutor y Libro guardados =)\n\n" + book);
            }
            Author authorExist = authorSerach.get();
            System.out.println("El Autor es: " + authorExist);
            var bookSearch = bookRepository.findByTitle(book.getTitle());
            if (bookSearch.isEmpty()) {
                System.out.println("El Libro es: " + book);
                authorExist.setBook(book);
                book.setAuthor(authorExist);
                bookRepository.save(book);
                System.out.println("\nLibro guardado =)\n\n" + book);
            }
            System.out.println("\nEl libro ya está registrado!\n\n" + bookSearch);
        }catch (NullPointerException e) {
            System.out.println("=(");
        }
    }

    public void searchAuthors() {
        List<Author> authors = authorRepository.findAll();
        if (authors.isEmpty()) {
            System.out.println("No se encontraron autores registrados.");
        }else {
            authors.forEach(a -> {
                System.out.println(a.toString());
            });
        }
    }

    public void searchBooks() {
        List<Book> booksSearch = bookRepository.findAll();
        if (booksSearch.isEmpty()) {
            System.out.println("No se encontraron libros registrados.");
        }else {
            booksSearch.forEach(b -> {
                System.out.println(b.toString());
            });
        }
    }

    public void searchBooksByLanguageInDataBase(String language) {
        List<Book> booksByLanguage = bookRepository.findByLanguageContains(language);
        if(booksByLanguage.isEmpty()) {
            System.out.println("\nNo se encontraron libros con el idioma seleccionado.");
        }else {
            System.out.println("\nCantidad de libros en el idioma seleccionado: " + booksByLanguage.size());
            booksByLanguage.forEach(b -> System.out.println(b.toString()));
        }
    }

    public void searchBooksByLanguage() {
        menu(getLanguagesOptions(), optionsLanguage);
        switch (option) {
            case 1: {
                searchBooksByLanguageInDataBase("es");
                break;
            }
            case 2: {
                searchBooksByLanguageInDataBase("en");
                break;
            }
            case 4: {
                searchBooksByLanguageInDataBase("fr");
                break;
            }
            case 5: {
                searchBooksByLanguageInDataBase("it");
                break;
            }

        }
    }



    public void livingAuthorsByYear() {
        System.out.println("\nIngrese el año en el que desea buscar los Autores vivos: \n");
        short yearSearch = -1;
        List<Author> livingAuthors = new ArrayList<>();
        if (!scanner.hasNextShort()) {
            System.out.println("'" + scanner.nextLine() + "'" + " no es un año válido para la búsqueda =(");
        } else {
            yearSearch = scanner.nextShort();
            livingAuthors = authorRepository.findAuthorsAliveInYear(yearSearch);
        }
        if (!(yearSearch > 2025 || yearSearch == -1 || livingAuthors.isEmpty())) {
            System.out.println("\nTotal de Autores vivos en el año " + yearSearch + ": " + livingAuthors.size());
            System.out.println("\n" + livingAuthors);
        }
        if (!(yearSearch == -1) & livingAuthors.isEmpty()) {
            System.out.println("\nNo se encontraron Autores vivos en el año: " + "'" + yearSearch + "'" + " registrados en la base de datos.");
        }
        if (yearSearch > 2025) {
            System.out.println("'" + yearSearch + "'" + ", no es un año válido para la búsqueda =(");
        }

    }

    public String getUserMenuOptions() {
        return """
                
                ##################__LITERALURA__##################
                Seleccione una opción:
                
                1. Buscar libro por título.
                2. Listar libros registrados.
                3. Listar autores resgistrados.
                4. Listar autores vivos en un determinado año.
                5. Listar libros por idioma.
                0. Salir.
                
                ###################################################
                """;
    }

    public String getLanguagesOptions() {
        return """
                
                ##################__LITERALURA__##################
                Seleccione un idioma:
                
                1. Español.
                2. Inglés.
                3. Francés.
                4. Italiano.
                5. Portugués.
                
                ###################################################
                """;
    }

    public Byte getUserInputOption() {
        return userInputOption;
    }

    public List<Byte> getOptions() {
        return options;
    }

    public Byte getOption() {
        return option;
    }
}
