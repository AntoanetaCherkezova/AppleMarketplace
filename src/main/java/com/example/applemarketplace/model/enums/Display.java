package com.example.applemarketplace.model.enums;

public enum Display {
    LCD("LCD (Liquid Crystal Display)"),
    RETINA_DISPLAY("Retina Display"),
    OLED("OLED (Organic Light-Emitting Diode)"),
    LIQUID_RETINA_HD("Liquid Retina HD"),
    LIQUID_RETINA_XDR("Liquid Retina XDR");

    private final String name;

    Display (String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
