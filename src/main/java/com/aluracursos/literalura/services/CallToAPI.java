package com.aluracursos.literalura.services;

import com.aluracursos.literalura.models.Query;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class CallToAPI {
    private String json = "";

    public void call(String search) throws IOException, InterruptedException {
        final String URL = "https://gutendex.com/books/?search=" + search;
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URL))
                .build();
        HttpResponse<String> response = client
                .send(request, HttpResponse.BodyHandlers.ofString());
        this.json = response.body();
    }
}
