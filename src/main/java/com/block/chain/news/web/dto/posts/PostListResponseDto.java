package com.block.chain.news.web.dto.posts;

import com.block.chain.news.domain.post.Post;
import lombok.Getter;

import java.util.List;

@Getter
public class PostListResponseDto {

    private List<Post> savedPost;
    private List<Post> otherPost;

    public PostListResponseDto(List<Post> savedPost, List<Post> otherPost){

        this.savedPost = savedPost;
        this.otherPost = otherPost;
    }
}
