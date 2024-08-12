package com.example.applemarketplace.model.enums;
import java.util.Locale;
import java.util.ResourceBundle;

public enum Role {
    ADMIN("enum.role.Admin"),
    USER("enum.role.User");

    private final String name;

    Role(String name) {
        this.name = name;
    }

    public String getName(Locale locale) {
        ResourceBundle messages = ResourceBundle.getBundle("messages", locale);
        return messages.getString(name);
    }
}
