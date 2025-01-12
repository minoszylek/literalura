package com.aluracursos.literalura.repository;

import com.aluracursos.literalura.models.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    @Override
    List<Author> findAll();

    Optional<Author> findByName(String name);
}
