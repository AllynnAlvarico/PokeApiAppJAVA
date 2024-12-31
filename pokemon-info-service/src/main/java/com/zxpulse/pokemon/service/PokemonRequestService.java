package com.zxpulse.pokemon.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zxpulse.pokemon.domain.PokemonModel;
import com.zxpulse.pokemon.domain.PokemonName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class PokemonRequestService {
    private static final Logger logger = LoggerFactory.getLogger(PokemonRequestService.class);
    private static final String POKEMON_API_URL = "https://pokeapi.co/api/v2/pokemon%s";
    private static final HttpClient clientService = HttpClient
            .newBuilder()
            .followRedirects(HttpClient.Redirect.ALWAYS)
            .build();
    private static final ObjectMapper pokemonObjMapper = new ObjectMapper();

    public List<PokemonName> requestPokemonNames() {
        String allPokemonUrl = "?limit=100000&offset=0";
        HttpRequest requestPokemon =
                HttpRequest
                        .newBuilder(URI.create(POKEMON_API_URL.formatted(allPokemonUrl)))
                        .GET()
                        .build();

        try {
            HttpResponse<String> response = clientService.send(requestPokemon, HttpResponse.BodyHandlers.ofString());
            return switch (response.statusCode()){
                case 200 -> getPokemon(response);
                case 404 -> List.of();
                default -> throw new RuntimeException("PokeApi call failed with status code " + response.statusCode());
            };
        }
        catch (IOException | InterruptedException e) {
            throw  new RuntimeException("Could not call Pokemon PokeApi", e);
        }
    }
    public  PokemonModel getInfoFor(String name){
        HttpRequest request = HttpRequest
                .newBuilder(URI.create(POKEMON_API_URL.formatted("/"+name)))
                .GET()
                .build();
        try{
            HttpResponse<String> response = clientService.send(request, HttpResponse.BodyHandlers.ofString());
//            logger.info(String.valueOf(PokemonRequestService.class));
            return switch (response.statusCode()){
                case 200 -> getPokemonModel(response);
                case 404 -> null;
                default -> throw new RuntimeException("PokeApi call failed with status code " + response.statusCode());
            };
        }
        catch (IOException | InterruptedException e) {
            throw new RuntimeException("Could not call Pokemon PokeApi Name", e);
        }
    }

    private List<PokemonName> getPokemon(HttpResponse<String> byRef_response) throws JsonProcessingException {
        JavaType returnObj = pokemonObjMapper.getTypeFactory().constructType(PokeApiResponse.class);
        PokeApiResponse pokeApiResponse = pokemonObjMapper.readValue(byRef_response.body(), returnObj);
        return pokeApiResponse.getResults();
    }
    private PokemonModel getPokemonModel(HttpResponse<String> byRef_response) throws JsonProcessingException {
        return pokemonObjMapper.readValue(byRef_response.body(), PokemonModel.class);
    }
}