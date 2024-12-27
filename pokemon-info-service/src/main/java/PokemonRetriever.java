import com.zxpulse.pokemon.PokemonResult;
import com.zxpulse.pokemon.PokemonRetrievalService;
import com.zxpulse.pokemon.PokemonStorageService;
import data.model.domain.Pokemon;
import repository.PokemonException;
import repository.PokemonRepository;

import java.util.List;
import java.util.stream.Collectors;

public class PokemonRetriever {
    public static void main(String[] args) {
        System.out.println("Getting all Pokemons!");

        try{
            retrievePokemons();
        } catch (Exception | PokemonException e){
            System.err.println("Unexpected Error!");
            e.printStackTrace();
        }
    }

    private static void retrievePokemons() throws PokemonException {
        PokemonRetrievalService pokemonRetrievalService = new PokemonRetrievalService();
        PokemonRepository pokemonRepository = PokemonRepository.openPokemonRepository("./pokemon.db");
        PokemonStorageService pokemonStorageService = new PokemonStorageService(pokemonRepository);

        List<PokemonResult> pokemonList =
                pokemonRetrievalService
                        .getAllPokemon()
                        .stream()
                        .toList();
        System.out.println("Pokemon size list " + pokemonList.size());
//        List<Pokemon> pokemonsToStore = pokemonList;


//        pokemonStorageService.storePokemons(pokemonList);

        pokemonList.forEach(e-> System.out.println(e.getName()));
    }
}
