package com.block.chain.news.web.dto.follow;

import com.block.chain.news.domain.post.Post;
import lombok.Getter;

import java.util.List;

@Getter
public class FollowingPostResponseDto {
    private String email;
    private List<Post> posts;

    public FollowingPostResponseDto(String email, List<Post> posts){
        this.email = email;
        this.posts = posts;
    }

}
