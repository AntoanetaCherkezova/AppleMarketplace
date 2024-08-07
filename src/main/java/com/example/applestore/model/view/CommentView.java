package com.example.applestore.model.view;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class CommentView {

    private Long id;
    private String userUsername;
    private String commentedAt;
    private String comment;
    private String userProfilePictureUrl;
}
