package com.zxpulse.pokemon.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zxpulse.pokemon.domain.PokemonModel;
import com.zxpulse.pokemon.domain.PokemonName;

import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public class PokeApiResponse {
    private List<PokemonName> results;
    private List<PokemonModel> pokemonModelList;

    public List<PokemonName> getResults() {
        return results;
    }

    public List<PokemonModel> getPokemonModelList() {
        return pokemonModelList;
    }
}