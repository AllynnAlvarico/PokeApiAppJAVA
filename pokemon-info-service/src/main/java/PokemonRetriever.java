import com.zxpulse.pokemon.domain.PokemonModel;
import com.zxpulse.pokemon.domain.PokemonName;
import com.zxpulse.pokemon.service.PokemonRequestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;


public class PokemonRetriever {

    private static final Logger logger = LoggerFactory.getLogger(PokemonRetriever.class);
    static PokemonRequestService pokemonRequestService = new PokemonRequestService();

    public static void main(String[] args) {
        logger.info("PokemonRetriever started");

        try{
            retrievingAllPokemon();
        } catch (Exception e) {
            logger.error("Error retrieving Pokemon names", e);
        }
    }

    private static void retrievingAllPokemon() {
        Pattern pattern = Pattern.compile("-");
        List<PokemonName> allPokemonNames;
        List<PokemonModel> pokemonModels = new ArrayList<>();

        logger.info("Getting all Pokémons!");
        long startTime = System.currentTimeMillis();

        try {
            allPokemonNames = retrievePokemonNames();
            int totalPokemon = allPokemonNames.size();

            logger.info("Total Pokémon to retrieve: {}", totalPokemon);

            for (int i = 0; i < totalPokemon; i++) {
                long requestStartTime = System.currentTimeMillis();
                PokemonName pokemon = allPokemonNames.get(i);

                if (!pokemon.getPokemonName().contains("-")) {
                    logger.info("Retrieved Pokémon: {}", pokemon.getPokemonName());

                    PokemonModel model = getEachPokemon(pokemon.getPokemonName());
                    if (model != null) {
                        pokemonModels.add(model);
                    } else {
                        logger.warn("PokemonModel for {} is null", pokemon.getPokemonName());
                    }

                    long requestEndTime = System.currentTimeMillis();
                    logger.info("Time taken for {}: {} seconds", pokemon.getPokemonName(),
                            (requestEndTime - requestStartTime) / 1000.0);
                }
            }



            for (PokemonModel model : pokemonModels) {
                logger.info("Id: {}", model.id());
                logger.info("Name: {}", model.name());
                logger.info("Height: {}", model.height());
                logger.info("Weight: {}", model.weight());
            }

            logger.info("Total Pokémon models retrieved: {}", pokemonModels.size());

            long endTime = System.currentTimeMillis();
            logger.info("Completed retrieving all Pokémon.");
            logger.info("Total time taken: {} seconds", (endTime - startTime) / 1000.0);

        } catch (IOException | InterruptedException e) {
            logger.error("Error retrieving Pokémon model information", e);
        }
    }



    private static List<PokemonName> retrievePokemonNames() throws IOException, InterruptedException {
        List<PokemonName> results;
        results =
                pokemonRequestService
                        .requestPokemonNames()
                        .stream().toList();
        return results;
    }
    public static PokemonModel getEachPokemon(String name){
        return pokemonRequestService
                .getInfoFor(name);
    }
}
