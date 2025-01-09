package com.aluracursos.literalura.models;

public class Author {
    private String name;
    private Short birthYear;
    private Short deathYear;

    public Author(AuthorData authorData) {
        this.name = authorData.name();
        this.birthYear = authorData.birthYear();
        this.deathYear = authorData.deathYear();
    }

    @Override
    public String toString() {
        return "Author: " +
                "name='" + name + '\'' +
                ", birthYear=" + birthYear +
                ", deathYear=" + deathYear;
    }
}
