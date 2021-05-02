package com.example.socialmedia.mapper;

import com.example.socialmedia.dto.PostRequest;
import com.example.socialmedia.dto.PostResponse;
import com.example.socialmedia.model.Post;
import com.example.socialmedia.model.User;
import com.example.socialmedia.repository.CommentRepository;
import com.example.socialmedia.repository.LikeRepository;
import com.example.socialmedia.service.AuthService;
import com.github.marlonlom.utilities.timeago.TimeAgo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.stream.Collectors;


@Mapper(componentModel = "spring")
public abstract class PostMapper {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private LikeRepository likeRepository;
    @Autowired
    private AuthService authService;
    private boolean liked = true;
    private boolean disliked = true;

    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "postData", source = "postRequest.postData")
    public abstract Post map(PostRequest postRequest, User user);

    @Mapping(target = "id", source = "postId")
    @Mapping(target = "userName", source = "user.username")
    @Mapping(target = "commentsCount", expression = "java(commentCount(post))")
    @Mapping(target="likesCount",expression = "java(likesCount(post))")
    @Mapping(target="dislikesCount",expression = "java(dislikesCount(post))")
    @Mapping(target = "duration", expression = "java(getDuration(post))")
    public abstract PostResponse mapToDto(Post post);

    public Integer commentCount(Post post) {
        return commentRepository.findByPost(post).size();
    }
    public Integer likesCount(Post post) {
         return likeRepository.findByPost(post).stream()
                 .filter(p-> p.isLiked()).collect(Collectors.toList()).size();
    }
    public Integer dislikesCount(Post post) {
        return likeRepository.findByPost(post).stream()
                .filter(p-> p.isDisliked()).collect(Collectors.toList()).size();
    }


      public String getDuration(Post post) {
        return TimeAgo.using(post.getCreatedDate().toEpochMilli());
    }

}