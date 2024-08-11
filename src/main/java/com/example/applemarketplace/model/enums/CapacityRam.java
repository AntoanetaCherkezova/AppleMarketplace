package com.example.applemarketplace.model.enums;

public enum CapacityRam {
    GB_1("1GB"),
    GB_2("2GB"),
    GB_3("3GB"),
    GB_4("4GB"),
    GB_6("6GB"),
    GB_8("8GB"),
    GB_16("16GB"),
    GB_64("64GB"),
    GB_128("128GB");

    private final String name;

    CapacityRam (String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
