package com.example.applemarketplace.model.enums;

import java.util.Locale;
import java.util.ResourceBundle;

public enum Colour {
    MIDNIGHT("enum.color.Midnight"),
    STARLIGHT("enum.color.Starlight"),
    BLUE("enum.color.Blue"),
    PURPLE("enum.color.Purple"),
    YELLOW("enum.color.Yellow"),
    SPACE_BLACK("enum.color.SpaceBlack"),
    SILVER("enum.color.Silver"),
    GOLD("enum.color.Gold"),
    DEEP_PURPLE("enum.color.DeepPurple"),
    PINK("enum.color.Pink"),
    GREEN("enum.color.Green"),
    GRAPHITE("enum.color.Graphite"),
    SIERRA_BLUE("enum.color.SierraBlue"),
    ALPINE_GREEN("enum.color.AlpineGreen"),
    SPACE_GRAY("enum.color.SpaceGray"),
    ORANGE("enum.color.Orange"),
    WHITE("enum.color.White"),
    BLACK("enum.color.Black");

    private final String name;

    Colour(String name) {
        this.name = name;
    }

    public String getName(Locale locale) {
        ResourceBundle messages = ResourceBundle.getBundle("messages", locale);
        return messages.getString(name);
    }
}
