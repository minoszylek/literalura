package com.aluracursos.literalura.services;

import com.aluracursos.literalura.models.Book;
import com.aluracursos.literalura.models.BookData;
import com.aluracursos.literalura.models.Query;

import java.io.IOException;
import java.util.*;
import java.util.stream.Stream;

public class ToolKit {
    private final List<Byte> options = List.of((byte) 1, (byte) 2, (byte) 3, (byte) 4, (byte) 5, (byte) 0);
    private final Scanner scanner = new Scanner(System.in);
    private Byte userInputOption = 9;
    private Byte option = 9;
    private String search = "";
    private final CallToAPI callToAPI = new CallToAPI();
    private final Deserialization deserialization = new Deserialization();
    List<Book> books;

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

    public void searchBookForTitle() throws IOException, InterruptedException {
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
            }
            book.ifPresent(System.out::println);
        }
        equalBook.ifPresent(System.out::println);
    }

    public String getUserMenuOptions() {
        return """
                
                #################################################
                Seleccione una opción:
                
                1. Buscar libro por título.
                2. Listar libros registrados.
                3. Listar autores resgistrados.
                4. Listar autores vivos en un determinado año.
                5. Listar libros por idioma.
                0. Salir.
                #################################################
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
