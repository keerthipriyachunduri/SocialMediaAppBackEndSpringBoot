package com.example.socialmedia.controller;

import com.example.socialmedia.dto.LikesDto;
import com.example.socialmedia.model.Likes;
import com.example.socialmedia.model.Post;
import com.example.socialmedia.service.LikesService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/likes")
@AllArgsConstructor
public class LikesController {

    private final LikesService likesService;

    @PostMapping
    public ResponseEntity<Void> likes(@RequestBody LikesDto likesDto) {
        likesService.likes(likesDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/by-post/{post}")
    public ResponseEntity<Optional<Likes>> getLikesForPost(@PathVariable Post post ) {
        return ResponseEntity.status(OK)
                .body(likesService.getLikesForPostByUsername(post));
    }




}