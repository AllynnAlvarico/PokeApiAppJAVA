package com.zxpulse.pokemon.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record PokemonModel(
        int id, String name, @JsonProperty("base_experience") int base_experience,
        double height, double weight, int order) {
    // Still need to add the rest of the fields
    // which are:
    // - abilities <List> PokemonAbility
    //           -> is_hidden <Boolean>
    //           -> slot <Integer>
    //           -> ability PokemonAbility
    //                   -> id <Integer>
    //                   -> name <String>
    //                   -> flavor_text_entries <List> PokemonFlavorText
    // - forms <List> PokemonForm
    //           -> slot <Integer>
    //           -> type PokemonType
    // - game_indices <List> PokemonGameIndex
    // - held_items <List> PokemonHeldItem
    // - location_area_encounters <String>
    // - moves <List> PokemonMove
    // - species PokemonSpecies
    // - sprites PokemonSprites
    // - stats <List> PokemonStat


}
