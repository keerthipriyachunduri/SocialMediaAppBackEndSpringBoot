package com.example.socialmedia.service;

import com.example.socialmedia.dto.CommentsDto;
import com.example.socialmedia.exception.PostNotFoundException;
import com.example.socialmedia.exception.SocialMediaException;
import com.example.socialmedia.mapper.CommentMapper;
import com.example.socialmedia.model.Comment;
import com.example.socialmedia.model.NotificationEmail;
import com.example.socialmedia.model.Post;
import com.example.socialmedia.model.User;
import com.example.socialmedia.repository.CommentRepository;
import com.example.socialmedia.repository.PostRepository;
import com.example.socialmedia.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
public class CommentService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final AuthService authService;
    private final CommentMapper commentMapper;
    private final MailContentBuilder mailContentBuilder;
    private final MailService mailService;
    private static final String POST_URL = "";
    public void save(CommentsDto commentsDto){

        Post post = postRepository.findById(commentsDto.getPostId())
                .orElseThrow(() -> new PostNotFoundException(commentsDto.getPostId().toString()));
        User user = userRepository.findByUsername(commentsDto.getUserName())
                .orElseThrow(() -> new SocialMediaException("Username not found"));
        System.out.println(user);
        Comment comment = commentMapper.map(commentsDto, post, user);
        System.out.println(comment);
        commentRepository.save(comment);
        String message = mailContentBuilder.build(user+ " posted a comment on your post." + POST_URL);
        sendCommentNotification(message, post.getUser());

    }
    private void sendCommentNotification(String message, User user) {
        mailService.sendMail(new NotificationEmail(user.getUsername() + " Commented on your post", user.getEmail(), message));
    }

    public List<CommentsDto> getAllCommentsForPost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException(postId.toString()));
        return commentRepository.findByPost(post)
                .stream()
                .map(commentMapper::mapToDto).collect(toList());
    }

    public List<CommentsDto> getAllCommentsForUser(String userName) {
        User user = userRepository.findByUsername(userName)
                .orElseThrow(() -> new UsernameNotFoundException(userName));
        return commentRepository.findAllByUser(user)
                .stream()
                .map(commentMapper::mapToDto)
                .collect(toList());
    }

}
