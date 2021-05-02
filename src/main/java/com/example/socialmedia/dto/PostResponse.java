package com.example.socialmedia.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {
    private Long id;
    private String postData;
    private String userName;
    private Integer likesCount;
    private Integer dislikesCount;
    private Integer commentsCount;
    private String duration;

}
