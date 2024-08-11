package com.example.applemarketplace.model.enums;

public enum Battery {
    LI_ION("Lithium-Ion (Li-Ion)"),
    LIPO("Lithium-Polymer (Li-Po)"),
    DUAL_CELL_LIPO("Dual-Cell Li-Po");

    private final String name;

    Battery (String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
