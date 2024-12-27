package com.zxpulse.pokemon;

import data.model.domain.Pokemon;
import repository.PokemonException;
import repository.PokemonRepository;

import java.util.List;

public class PokemonStorageService {

    private final PokemonRepository pokemonRepository;

    public PokemonStorageService(PokemonRepository byRef_pokemonRepository) {
        this.pokemonRepository = byRef_pokemonRepository;
    }
    public void storePokemons(List<PokemonResult> byRef_pokemon) throws PokemonException {
        for(PokemonResult p: byRef_pokemon) {
            Pokemon pokemon =
                    new Pokemon(
                            p.getId(),
                            p.getName(),
                            p.getOrder(),
                            p.getType(),
                            p.getUrl());
            pokemonRepository.savePokemon(pokemon);
        }
    }

}
