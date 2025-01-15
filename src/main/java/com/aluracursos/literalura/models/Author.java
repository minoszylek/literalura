package com.aluracursos.literalura.models;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;

@Entity
@Table(name= "authors")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String name;
    private Integer birthYear;
    private Integer deathYear;
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Book> books = new ArrayList<>();

    public Author(AuthorData authorData) {
        this.name = authorData.name();
        try{
            this.birthYear = OptionalInt.of(Integer.valueOf(authorData.birthYear())).orElse(0);
        } catch (NullPointerException e) {
            this.birthYear = null;
        }
        try {
            this.deathYear = OptionalInt.of(Integer.valueOf(authorData.deathYear())).orElse(0);
        } catch (NullPointerException e) {
            this.deathYear = null;
        }

    }

    public Author() {
    }

    public String getName() {
        return name;
    }

    public Integer getBirthYear() {
        return birthYear;
    }

    public Integer getDeathYear() {
        return deathYear;
    }

    public List<Book> getBook() {
        return books;
    }

    public void setBook (Book book) {
        this.books.add(book);
        book.setAuthor(this);
    }

    @Override
    public String toString() {
        return  "\n--------------------AUTOR--------------------" +
                "\n Nombre: '" + name + '\'' +
                "\n Fecha de nacimiento: " + birthYear +
                "\n Fecha de muerte: " + deathYear +
                "\n Libros: =" + books.stream().map(Book::getTitle).toList() +
                "\n---------------------------------------------\n";
    }
}
