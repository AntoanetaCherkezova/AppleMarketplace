package com.example.applestore.service;
import com.example.applestore.model.dtos.CommentAddDTO;
import com.example.applestore.model.entity.Comment;
import com.example.applestore.model.entity.Device;
import com.example.applestore.model.entity.User;
import com.example.applestore.model.view.CommentView;
import com.example.applestore.repository.CommentRepository;
import com.example.applestore.service.interfaces.CommentService;
import com.example.applestore.service.interfaces.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, ModelMapper modelMapper, UserService userService) {
        this.commentRepository = commentRepository;
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    @Override
    public Comment createComment(Device device, String username, CommentAddDTO commentDto) {
        Comment comment = modelMapper.map(commentDto, Comment.class);

        User user = this.userService.findByUsername(username).get();
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        comment.setCommentedAt(LocalDateTime.now());
        comment.setUser(user);

        return comment;
    }

    @Override
    public List<CommentView> getCommentsForDevice(Device device) {
        List<Comment> comments = device.getComments();
        Collections.reverse(comments);
        return comments.stream()
                .map(comment -> modelMapper.map(comment, CommentView.class))
                .collect(Collectors.toList());
    }

    @Override
    public CommentView createCommentView(Comment comment) {
        return modelMapper.map(comment,CommentView.class);
    }

    @Override
    public Comment findById(Long commentId) {
        return this.commentRepository.findById(commentId).orElse(null);
    }

}
