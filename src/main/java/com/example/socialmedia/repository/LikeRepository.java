package com.example.socialmedia.repository;

import com.example.socialmedia.model.Likes;
import com.example.socialmedia.model.Post;
import com.example.socialmedia.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Likes,Long> {

    List<Likes> findByPost(Post post);

    Optional<Likes> findTopByPostAndUserOrderByLikesIdDesc(Post post, User currentUser);
}
