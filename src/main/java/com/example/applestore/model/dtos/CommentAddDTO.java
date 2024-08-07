package com.example.applestore.model.dtos;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Getter
@Setter
@Accessors(chain = true)
public class CommentAddDTO {

    private String user;
    private LocalDateTime commentedAt;
    private String comment;
}
