package com.example.applemarketplace.model.enums;

import java.util.Locale;
import java.util.ResourceBundle;

public enum InternalMemory {
    GB_32("enum.internalMemory.32GB"),
    GB_64("enum.internalMemory.64GB"),
    GB_128("enum.internalMemory.128GB"),
    GB_256("enum.internalMemory.256GB"),
    GB_512("enum.internalMemory.512GB"),
    TB_1("enum.internalMemory.1TB"),
    TB_2("enum.internalMemory.2TB"),
    TB_4("enum.internalMemory.4TB"),
    TB_16("enum.internalMemory.16TB");

    private final String name;

    InternalMemory (String name) {
        this.name = name;
    }

    public String getName(Locale locale) {
        ResourceBundle messages = ResourceBundle.getBundle("messages", locale);
        return messages.getString(name);
    }
}
