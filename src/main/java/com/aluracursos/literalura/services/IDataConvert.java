package com.aluracursos.literalura.services;

public interface IDataConvert {
    <T> T deserialzationData (String json, Class<T> tClass);
}
