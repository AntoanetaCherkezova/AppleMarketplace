package com.example.applestore.model.enums;

public enum InternalMemory {
    GB_32("32GB"),
    GB_64("64GB"),
    GB_128("128GB"),
    GB_256("256GB"),
    GB_512("512GB"),
    TB_1("1TB"),
    TB_2("2TB"),
    TB_4("4TB"),
    TB_16("16TB");

    private final String name;

    InternalMemory (String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
