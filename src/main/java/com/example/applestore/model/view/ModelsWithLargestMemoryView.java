package com.example.applestore.model.view;

import com.example.applestore.model.enums.InternalMemory;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class ModelsWithLargestMemoryView {

    private List<String> photosUrls;
    private String model;
    private InternalMemory internalMemory;
}
