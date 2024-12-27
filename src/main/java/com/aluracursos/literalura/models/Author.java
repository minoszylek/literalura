package com.aluracursos.literalura.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.util.IgnorePropertiesUtil;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Author(
        @JsonAlias("name") String name,
        @JsonAlias("birth_year") Short birthYear,
        @JsonAlias("death_year") Short deathYear
) {
}
