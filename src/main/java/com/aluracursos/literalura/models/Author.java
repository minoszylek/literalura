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
    private List<Book> book = new ArrayList<>();

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
        return book;
    }

    public void setBook (Book book) {
        this.book.add(book);
        book.setAuthor(this);
    }

    @Override
    public String toString() {
        return "Author: " +
                "dbId=" + id +
                ", name='" + name + '\'' +
                ", birthYear=" + birthYear +
                ", deathYear=" + deathYear;
    }
}
