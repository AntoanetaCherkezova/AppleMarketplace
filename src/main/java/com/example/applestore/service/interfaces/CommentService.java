package com.example.applestore.service.interfaces;
import com.example.applestore.model.dtos.CommentAddDTO;
import com.example.applestore.model.entity.Comment;
import com.example.applestore.model.entity.Device;
import com.example.applestore.model.view.CommentView;

import java.util.List;

public interface CommentService {
    Comment createComment(Device device, String username, CommentAddDTO commentDto);

    List<CommentView> getCommentsForDevice(Device device);

    CommentView createCommentView(Comment comment);

    Comment findById(Long commentId);


}
