package com.zxpulse.pokemon;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;


public class PokemonRetrievalService {
    private static final String pokeApiUrl = "https://pokeapi.co/api/v2/pokemon%s";
    private static final HttpClient CLIENT =
            HttpClient
                    .newBuilder()
                    .followRedirects(HttpClient.Redirect.ALWAYS)
                    .build();
    private static final ObjectMapper pokemonObjMapper = new ObjectMapper();

    public List<PokemonResult> getAllPokemon(){
        String allPokemon = "?limit=100000&offset=0";
        HttpRequest requestPokemon =
                HttpRequest
                        .newBuilder(URI.create(pokeApiUrl.formatted(allPokemon)))
                        .GET()
                        .build();
        try {
            HttpResponse<String> response = CLIENT.send(requestPokemon, HttpResponse.BodyHandlers.ofString());
            return switch (response.statusCode()){
                case 200 -> getPokemons(response);
                case 404 -> List.of();
                default -> throw new RuntimeException("PokeApi call failed with status code " + response.statusCode());
            };
        } catch (IOException | InterruptedException e){
            throw  new RuntimeException("Could not call Pokemon PokeApi", e);
        }
    }

    private static List<PokemonResult> getPokemons(HttpResponse<String> byRef_request) throws JsonProcessingException {
        JavaType returnObj = pokemonObjMapper.getTypeFactory().constructType(PokeApiResponse.class);
        PokeApiResponse pokeApiResponse = pokemonObjMapper.readValue(byRef_request.body(), returnObj);
        return pokeApiResponse.getResults();
    }

}
