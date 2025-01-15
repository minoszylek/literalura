package com.aluracursos.literalura.models;

import jakarta.persistence.*;

@Entity
@Table (name = "books")
public class Book {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer gutendexId;
    @Column(unique = true)
    private String title;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;
    private String language;
    private Integer downloads;

    public Book (BookData bookData) {
        this.gutendexId = bookData.id();
        this.title = bookData.title();
        this.author = bookData.authors()
                .stream()
                .map(Author::new).toList().get(0);
        this.language = bookData.languages().get(0).trim();
        this.downloads = bookData.downloads();

    }

    public Book() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Integer getDownloads() {
        return downloads;
    }

    public void setDownloads(Integer downloads) {
        this.downloads = downloads;
    }

    @Override
    public String toString() {
        return  "\n____________________LIBRO____________________" +
                "\n Título: '" + title + '\'' +
                "\n Autor: " + author.getName() +
                "\n Lenguaje: " + language +
                "\n Total descargas:" + downloads  + "\n";
    }
}
