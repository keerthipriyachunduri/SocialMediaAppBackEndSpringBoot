package com.example.socialmedia.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LikesDto {
    private Long likeId;
    private boolean liked;
    private boolean disliked;
    private Long postId;
}
