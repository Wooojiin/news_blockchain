package com.block.chain.news.web.dto.posts;

import com.block.chain.news.domain.post.Post;
import lombok.Getter;

import java.util.List;

@Getter
public class KindsResponseDto {
    String kinds;
    List<Post> posts;

    public KindsResponseDto(String kinds, List<Post> posts){
        this.kinds = kinds;
        this.posts = posts;
    }
}
