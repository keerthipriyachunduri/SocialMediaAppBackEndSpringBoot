package com.example.socialmedia.mapper;


import com.example.socialmedia.dto.LikesDto;
import com.example.socialmedia.model.Likes;
import com.example.socialmedia.model.Post;
import com.example.socialmedia.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LikesMapper {
    @Mapping(target = "likesId", ignore = true)
    @Mapping(target = "liked", source = "likesDto.liked")
    @Mapping(target = "disliked", source = "likesDto.disliked")
    @Mapping(target = "post", source = "post")
    Likes map(LikesDto likesDto, Post post, User user);

    @Mapping(target = "postId", expression = "java(likes.getPost().getPostId())")
    LikesDto mapToDto(Likes likes);
}
