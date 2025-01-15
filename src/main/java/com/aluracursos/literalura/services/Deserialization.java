package com.aluracursos.literalura.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Deserialization implements IDataConvert {
     private final ObjectMapper mapper = new ObjectMapper();
    @Override
    public <T> T deserialzationData(String json, Class<T> tClass) {
        try {
            return mapper.readValue(json, tClass);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
