package com.zxpulse.pokemon;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PokemonResult {
    private String id;
    private String name;
    private String order;
    private String type;
    private String url;

    public String getId() {
        return id;
    }

    public String getOrder() {
        return order;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
