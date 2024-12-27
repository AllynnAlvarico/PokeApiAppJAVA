package repository;

import data.model.domain.Pokemon;

import java.util.List;

public interface PokemonRepository {

    void savePokemon(Pokemon pokemon) throws PokemonException;
    List<Pokemon> getAllPokemons() throws PokemonException;
    static PokemonRepository openPokemonRepository(String database){
        return new PokemonJdbcRepository(database);
    }
}
