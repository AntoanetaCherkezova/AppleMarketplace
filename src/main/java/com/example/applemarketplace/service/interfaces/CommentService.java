package com.example.applemarketplace.service.interfaces;
import com.example.applemarketplace.model.dtos.CommentAddDTO;
import com.example.applemarketplace.model.entity.Comment;
import com.example.applemarketplace.model.entity.Device;
import com.example.applemarketplace.model.view.CommentView;

import java.util.List;

public interface CommentService {
    Comment createComment(Device device, String username, CommentAddDTO commentDto);

    List<CommentView> getCommentsForDevice(Device device);

    CommentView createCommentView(Comment comment);

    Comment findById(Long commentId);


}
