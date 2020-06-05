package com.block.chain.news.web.dto.posts;

import com.block.chain.news.domain.post.Post;
import lombok.Getter;

import java.util.List;

@Getter
public class FollowerPostResponseDto {
    private String topics;

    private List<Post> posts;

    public FollowerPostResponseDto(String topics, List<Post> posts){
        this.topics = topics;
        this.posts = posts;
    }
}
