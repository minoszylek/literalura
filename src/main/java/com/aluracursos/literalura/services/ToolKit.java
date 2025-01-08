package com.aluracursos.literalura.services;

import com.aluracursos.literalura.models.Book;
import com.aluracursos.literalura.models.Query;

import java.io.IOException;
import java.util.*;

public class ToolKit {
    private final List<Byte> options = List.of((byte) 1, (byte) 2, (byte) 3, (byte) 4, (byte) 5, (byte) 0);
    private final Scanner scanner = new Scanner(System.in);
    private Byte userInputOption = 9;
    private Byte option = 9;
    private String search = "";
    private final CallToAPI callToAPI = new CallToAPI();
    private final Deserialization deserialization = new Deserialization();

    public void menu(String menu, List<Byte> options) {

        while (option != 0) {
            System.out.println(menu);
            try {
                this.userInputOption = scanner.nextByte();
                if (userInputOption == 0) {
                    this.option = userInputOption;
                    scanner.nextLine();
                    break;
                }
                if (options.contains(userInputOption)) {
                    this.option = userInputOption;
                    scanner.nextLine();
                    break;
                } else {
                    System.out.println("'" + userInputOption + "'" + " no es una opción válida.\n¡Debes ingresar un número de las opciones del menú!");
                    scanner.nextLine();
                }
            } catch (InputMismatchException e) {
                String invalidInput = scanner.nextLine();
                System.out.println("'"+ invalidInput +"'" +" no es una opción válida.\n¡Solo debes ingresar los números del menú!");
                break;
            }
        }
    }

    public void searchBookForTitle() throws IOException, InterruptedException {
        System.out.println("Ingresa el nombre del libro que deseas buscar: ");
        this.search = scanner.next();
        var books = deserialization.deserialzationData(callToAPI.call(search.replace(" ", "+")), Query.class);

        books.books()
                .stream()
                .sorted(Comparator.comparing(Book::downloads)
                        .reversed())
                .limit(1)
                .forEach(b -> System.out.println(b.toString()));
        scanner.nextLine();
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
