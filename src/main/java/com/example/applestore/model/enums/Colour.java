package com.example.applestore.model.enums;

public enum Colour {
    MIDNIGHT("Midnight"),
    STARLIGHT("Starlight"),
    BLUE("Blue"),
    PURPLE("Purple"),
    RED("(Product) RED"),
    YELLOW("Yellow"),
    SPACE_BLACK("Space Black"),
    SILVER("Silver"),
    GOLD("Gold"),
    DEEP_PURPLE("Deep Purple"),
    PINK("Pink"),
    GREEN("Green"),
    GRAPHITE("Graphite"),
    SIERRA_BLUE("Sierra Blue"),
    ALPINE_GREEN("Alpine Green"),
    SPACE_GRAY("Space Gray"),
    ORANGE("Orange"),
    WHITE("White"),
    BLACK("Black");

    private final String name;

    Colour(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
