package com.aluracursos.literalura.services;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class ToolKit {
    private final List<Byte> options = List.of((byte) 1, (byte) 2, (byte) 3, (byte) 4, (byte) 5, (byte) 0);
    private final Scanner scanner = new Scanner(System.in);
    private Byte userInputOption = 9;
    private Byte option = 9;

    public String getUserMenuOptions() {
        return """
                #################################################
                Seleccione la opción de preferencia:
                
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

    public void menu(String menu, List<Byte> options) {

        while (option != 0) {
            System.out.println(menu);
            try {
                this.userInputOption = scanner.nextByte();
                if (userInputOption == 0) {
                    this.option = userInputOption;
                    break;
                }
                if (options.contains(userInputOption)) {
                    this.userInputOption = option;
                    break;
                } else {
                    System.out.println(option + " no es una opción válida, debes ingresar un número de las opciones del menú!");
                    scanner.nextLine();
                }
            } catch (InputMismatchException e) {
                System.out.println("Solo debes ingresar números para seleccionar una opción!");
                scanner.nextLine();
            }
        }
    }
}
