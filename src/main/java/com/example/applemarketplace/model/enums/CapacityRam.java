package com.example.applemarketplace.model.enums;

import java.util.Locale;
import java.util.ResourceBundle;

public enum CapacityRam {
    GB_1("enum.capacityRam.1GB"),
    GB_2("enum.capacityRam.2GB"),
    GB_3("enum.capacityRam.3GB"),
    GB_4("enum.capacityRam.4GB"),
    GB_6("enum.capacityRam.6GB"),
    GB_8("enum.capacityRam.8GB"),
    GB_16("enum.capacityRam.16GB"),
    GB_64("enum.capacityRam.64GB"),
    GB_128("enum.capacityRam.128GB");

    private final String name;

    CapacityRam (String name) {
        this.name = name;
    }

    public String getName(Locale locale) {
        ResourceBundle messages = ResourceBundle.getBundle("messages", locale);
        return messages.getString(name);
    }
}
