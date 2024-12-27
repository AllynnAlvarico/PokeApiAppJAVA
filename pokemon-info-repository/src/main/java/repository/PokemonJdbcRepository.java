package repository;

import data.model.domain.Pokemon;
import org.h2.jdbcx.JdbcDataSource;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class PokemonJdbcRepository implements PokemonRepository {

    private static final String H2_DATABASE_URL =
            "jdbc:h2:file%s;AUTO_SERVER=TRUE;INIT=RUNSCRIPT FROM './db_init.sql'";
    private static final String INSERT_POKEMON = """
            MERGE INTO Courses (id, name, order, type, url)
            VALUES (?, ?, ?, ?, ?)
            """;
    private final DataSource dataSource;

    PokemonJdbcRepository(String databaseFile) {
        JdbcDataSource jdbcDataSource = new JdbcDataSource();
        jdbcDataSource.setURL(H2_DATABASE_URL.formatted(databaseFile));
        this.dataSource = jdbcDataSource;

    }

    @Override
    public void savePokemon(Pokemon pokemon) throws PokemonException {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(INSERT_POKEMON);
            statement.setString(1, pokemon.id());
            statement.setString(2, pokemon.name());
            statement.setString(3, pokemon.order());
            statement.setString(4, pokemon.type());
            statement.setString(5, pokemon.spriteUrl());
            statement.execute();
        } catch (SQLException e) {
            throw new PokemonException("Failed to save " + pokemon, e);
        }
    }

    @Override
    public List<Pokemon> getAllPokemons() throws PokemonException {
        // Refer to Line 51
        try (Connection connection = dataSource.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM COURSES");


            List<Pokemon> pokemons = new ArrayList<>();
            while (resultSet.next()) {
                Pokemon pokemon =
                        new Pokemon(
                                resultSet.getString(1),
                                resultSet.getString(2),
                                resultSet.getString(3),
                                resultSet.getNString(4),
                                resultSet.getNString(5)
                        );
                pokemons.add(pokemon);

            }
            return Collections.unmodifiableList(pokemons);
        }
        catch(SQLException e){
            throw new PokemonException("Failed to retrieve courses", e);
        }
    }
}