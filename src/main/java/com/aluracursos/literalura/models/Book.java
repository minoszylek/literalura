package com.aluracursos.literalura.models;

import java.util.List;

public class Book {
    private Integer id;
    private String title;
    private List<Author> authors;
    private List<String> languages;
    private Integer downloads;

    public Book (BookData bookData) {
        this.id = bookData.id();
        this.title = bookData.title();
        this.languages = bookData.languages();
        this.downloads = bookData.downloads();
        this.authors = bookData.authors()
                .stream()
                .map(a -> new Author(a)).toList();
    }

    @Override
    public String toString() {
        return "Book: " +
                "id=" + id +
                ", title='" + title + '\'' +
                ", authors=" + authors +
                ", languages=" + languages +
                ", downloads=" + downloads;
    }
}
