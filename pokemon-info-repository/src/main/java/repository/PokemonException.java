package repository;

import java.sql.SQLException;

public class PokemonException extends Throwable {
    public PokemonException(String message, SQLException e) {
        super(message, e);
    }
}
