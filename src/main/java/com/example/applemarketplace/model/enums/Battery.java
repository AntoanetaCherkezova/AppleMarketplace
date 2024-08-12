package com.example.applemarketplace.model.enums;

import java.util.Locale;
import java.util.ResourceBundle;

public enum Battery {
    LI_ION("enum.battery.Lithium-Ion(Li-Ion)"),
    LIPO("enum.battery.Lithium-Polymer(Li-Po)"),
    DUAL_CELL_LIPO("enum.battery.Dual-CellLi-Po");

    private final String name;

    Battery (String name) {
        this.name = name;
    }

    public String getName(Locale locale) {
        ResourceBundle messages = ResourceBundle.getBundle("messages", locale);
        return messages.getString(name);
    }
}
