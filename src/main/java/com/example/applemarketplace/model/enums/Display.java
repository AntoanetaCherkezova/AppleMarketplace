package com.example.applemarketplace.model.enums;

import java.util.Locale;
import java.util.ResourceBundle;

public enum Display {
    LCD("enum.display.LCD(LiquidCrystalDisplay)"),
    RETINA_DISPLAY("enum.display.RetinaDisplay"),
    OLED("enum.display.OLED(OrganicLight-EmittingDiode)"),
    LIQUID_RETINA_HD("enum.display.LiquidRetinaHD"),
    LIQUID_RETINA_XDR("enum.display.LiquidRetinaXDR");

    private final String name;

    Display (String name) {
        this.name = name;
    }

    public String getName(Locale locale) {
        ResourceBundle messages = ResourceBundle.getBundle("messages", locale);
        return messages.getString(name);
    }
}
