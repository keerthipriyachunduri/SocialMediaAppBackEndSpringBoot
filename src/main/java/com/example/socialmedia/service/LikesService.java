package com.example.socialmedia.service;

import com.example.socialmedia.dto.LikesDto;
import com.example.socialmedia.exception.PostNotFoundException;
import com.example.socialmedia.model.Likes;
import com.example.socialmedia.model.Post;
import com.example.socialmedia.repository.LikeRepository;
import com.example.socialmedia.repository.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class LikesService {
    private final LikeRepository likeRepository;
    private final PostRepository postRepository;
    private final AuthService authService;

    @Transactional
    public void likes(LikesDto likesDto) {
        System.out.println("keerthi");
        Post post = postRepository.findById(likesDto.getPostId()).
                orElseThrow(() -> new PostNotFoundException("Post Not Found with ID - " + likesDto.getPostId()));
        Optional<Likes> likesByPostAndUser = likeRepository.findTopByPostAndUserOrderByLikesIdDesc(post, authService.getCurrentUser());
        if(likesByPostAndUser.isPresent()){
            likesDto.setLikeId(likesByPostAndUser.get().getLikesId());
        }
        if (likesByPostAndUser.isPresent()&&
                likesByPostAndUser.get().isLiked() && likesDto.isLiked()) {

                likesDto.setLiked(false);
                likesDto.setDisliked(false);

        }
        else if(likesByPostAndUser.isPresent()&&
                likesByPostAndUser.get().isDisliked() && likesDto.isDisliked()){

            likesDto.setLiked(false);
            likesDto.setDisliked(false);
        }
        else if(likesByPostAndUser.isPresent()&&
                likesByPostAndUser.get().isDisliked() && likesDto.isLiked()){
            likesDto.setLiked(true);
            likesDto.setDisliked(false);
        }
        else if(likesByPostAndUser.isPresent()&&
                likesByPostAndUser.get().isLiked() && likesDto.isDisliked()){
            likesDto.setLiked(false);
            likesDto.setDisliked(true);
        }

        likeRepository.save(mapToLikes(likesDto, post));


    }
    private Likes mapToLikes(LikesDto likesDto, Post post){
        return Likes.builder()
                .likesId(likesDto.getLikeId())
                .liked(likesDto.isLiked())
                .disliked(likesDto.isDisliked())
                .post(post)
                .user(authService.getCurrentUser())
                .build();
    }

    @Transactional
    public Optional<Likes> getLikesForPostByUsername(Post post) {
        Optional <Likes> likesByPostAndUser= likeRepository.findTopByPostAndUserOrderByLikesIdDesc(post, authService.getCurrentUser());
        return  likesByPostAndUser;
    }




}
