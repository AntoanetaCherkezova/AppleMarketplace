package com.example.applemarketplace.service;
import com.example.applemarketplace.model.dtos.CommentAddDTO;
import com.example.applemarketplace.model.entity.*;
import com.example.applemarketplace.model.view.CommentView;
import com.example.applemarketplace.repository.CommentRepository;
import com.example.applemarketplace.service.interfaces.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.ActiveProfiles;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class CommentServiceImplTest {
    @Mock
    private CommentRepository commentRepository;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private UserService userService;
    @InjectMocks
    private CommentServiceImpl commentService;

    @Captor
    private ArgumentCaptor<Comment> commentCaptor;

    private CommentAddDTO commentAddDTO;
    private Comment comment;
    private User user;
    private Iphone iphone;
    private MacBook macBook;
    private Watch watch;


    @BeforeEach
    void setUp() {
        user = new User();
        user.setUsername("testUsername");

        commentAddDTO = new CommentAddDTO();
        commentAddDTO.setComment("This is a comment");

        comment = new Comment();
        comment.setComment("This is a comment");
        comment.setCommentedAt(LocalDateTime.now());

        iphone = new Iphone();
        iphone.setComments(new ArrayList<>());

        macBook = new MacBook();
        macBook.setComments(new ArrayList<>());

        watch = new Watch();
        watch.setComments(new ArrayList<>());
    }

    @Test
    void createCommentForIphone() {
        when(userService.findByUsername("testUsername")).thenReturn(Optional.of(user));
        when(modelMapper.map(commentAddDTO, Comment.class)).thenReturn(comment);

        Comment result = commentService.createComment(iphone, "testUsername", commentAddDTO);

        assertNotNull(result);
        assertEquals("This is a comment", result.getComment());
        assertEquals(user, result.getUser());
        assertNotNull(result.getCommentedAt());
        assertFalse(iphone.getComments().contains(result));

        verify(userService).findByUsername("testUsername");
        verify(modelMapper).map(commentAddDTO, Comment.class);
    }

    @Test
    void createCommentForMacBook() {
        when(userService.findByUsername("testUsername")).thenReturn(Optional.of(user));
        when(modelMapper.map(commentAddDTO, Comment.class)).thenReturn(comment);

        Comment result = commentService.createComment(macBook, "testUsername", commentAddDTO);

        assertNotNull(result);
        assertEquals("This is a comment", result.getComment());
        assertEquals(user, result.getUser());
        assertNotNull(result.getCommentedAt());
        assertFalse(macBook.getComments().contains(result));

        verify(userService).findByUsername("testUsername");
        verify(modelMapper).map(commentAddDTO, Comment.class);
    }

    @Test
    void createCommentForWatch() {
        when(userService.findByUsername("testUsername")).thenReturn(Optional.of(user));
        when(modelMapper.map(commentAddDTO, Comment.class)).thenReturn(comment);

        Comment result = commentService.createComment(watch, "testUsername", commentAddDTO);

        assertNotNull(result);
        assertEquals("This is a comment", result.getComment());
        assertEquals(user, result.getUser());
        assertNotNull(result.getCommentedAt());
        assertFalse(watch.getComments().contains(result));

        verify(userService).findByUsername("testUsername");
        verify(modelMapper).map(commentAddDTO, Comment.class);
    }

    @Test
    void getCommentsForIphone() {
        Comment comment1 = new Comment();
        comment1.setComment("First comment");
        comment1.setCommentedAt(LocalDateTime.now().minusDays(1));

        Comment comment2 = new Comment();
        comment2.setComment("Second comment");
        comment2.setCommentedAt(LocalDateTime.now());

        iphone.setComments(Arrays.asList(comment1, comment2));

        CommentView commentView1 = new CommentView();
        commentView1.setComment("First comment");

        CommentView commentView2 = new CommentView();
        commentView2.setComment("Second comment");

        when(modelMapper.map(comment1, CommentView.class)).thenReturn(commentView1);
        when(modelMapper.map(comment2, CommentView.class)).thenReturn(commentView2);

        List<CommentView> result = commentService.getCommentsForDevice(iphone);

        assertEquals(2, result.size());
        assertEquals("Second comment", result.get(0).getComment());
        assertEquals("First comment", result.get(1).getComment());

        verify(modelMapper).map(comment1, CommentView.class);
        verify(modelMapper).map(comment2, CommentView.class);
    }

    @Test
    void getCommentsForMacBook() {
        Comment comment1 = new Comment();
        comment1.setComment("First comment");
        comment1.setCommentedAt(LocalDateTime.now().minusDays(1));

        Comment comment2 = new Comment();
        comment2.setComment("Second comment");
        comment2.setCommentedAt(LocalDateTime.now());

        macBook.setComments(Arrays.asList(comment1, comment2));

        CommentView commentView1 = new CommentView();
        commentView1.setComment("First comment");

        CommentView commentView2 = new CommentView();
        commentView2.setComment("Second comment");

        when(modelMapper.map(comment1, CommentView.class)).thenReturn(commentView1);
        when(modelMapper.map(comment2, CommentView.class)).thenReturn(commentView2);

        List<CommentView> result = commentService.getCommentsForDevice(macBook);

        assertEquals(2, result.size());
        assertEquals("Second comment", result.get(0).getComment());
        assertEquals("First comment", result.get(1).getComment());

        verify(modelMapper).map(comment1, CommentView.class);
        verify(modelMapper).map(comment2, CommentView.class);
    }

    @Test
    void getCommentsForWatch() {
        Comment comment1 = new Comment();
        comment1.setComment("First comment");
        comment1.setCommentedAt(LocalDateTime.now().minusDays(1));

        Comment comment2 = new Comment();
        comment2.setComment("Second comment");
        comment2.setCommentedAt(LocalDateTime.now());

        watch.setComments(Arrays.asList(comment1, comment2));

        CommentView commentView1 = new CommentView();
        commentView1.setComment("First comment");

        CommentView commentView2 = new CommentView();
        commentView2.setComment("Second comment");

        when(modelMapper.map(comment1, CommentView.class)).thenReturn(commentView1);
        when(modelMapper.map(comment2, CommentView.class)).thenReturn(commentView2);

        List<CommentView> result = commentService.getCommentsForDevice(watch);

        assertEquals(2, result.size());
        assertEquals("Second comment", result.get(0).getComment());
        assertEquals("First comment", result.get(1).getComment());

        verify(modelMapper).map(comment1, CommentView.class);
        verify(modelMapper).map(comment2, CommentView.class);
    }

    @Test
    void createCommentView() {
        Comment comment = new Comment();
        comment.setComment("A comment");

        CommentView commentView = new CommentView();
        commentView.setComment("A comment");

        when(modelMapper.map(comment, CommentView.class)).thenReturn(commentView);

        CommentView result = commentService.createCommentView(comment);

        assertNotNull(result);
        assertEquals("A comment", result.getComment());

        verify(modelMapper).map(comment, CommentView.class);
    }

    @Test
    void findById() {
        Comment comment = new Comment();
        comment.setComment("Existing comment");

        when(commentRepository.findById(1L)).thenReturn(Optional.of(comment));

        Comment result = commentService.findById(1L);

        assertNotNull(result);
        assertEquals("Existing comment", result.getComment());

        verify(commentRepository).findById(1L);
    }

    @Test
    void findByIdNotFound() {
        when(commentRepository.findById(1L)).thenReturn(Optional.empty());

        Comment result = commentService.findById(1L);

        assertNull(result);

        verify(commentRepository).findById(1L);
    }
}
