package data.model.domain;

public record Pokemon(String id, String name, String order, String type, String spriteUrl) {
    public Pokemon{
        filled(id);
        filled(name);
        filled(order);
        filled(type);
        filled(spriteUrl);
    }

    private static void filled(String s){
        if(s == null || s.isBlank()){
            throw new IllegalArgumentException("No value present!");
        }
    }
}

