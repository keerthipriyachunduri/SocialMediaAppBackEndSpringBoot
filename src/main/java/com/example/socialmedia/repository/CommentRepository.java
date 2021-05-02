package com.example.socialmedia.repository;

import com.example.socialmedia.model.Comment;
import com.example.socialmedia.model.Post;
import com.example.socialmedia.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findByPost(Post post);


    List<Comment> findAllByUser(User user);
;
}
