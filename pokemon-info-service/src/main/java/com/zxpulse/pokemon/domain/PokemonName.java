package com.zxpulse.pokemon.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)

public record PokemonName(String name, String url) {

    public PokemonName {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Pokemon name cannot be null or empty");
        }
    }

    public String getPokemonName() {
        return name;
    }
    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return
                "Pokemon Name: " + name +
                ", URL: " + url;
    }
}
