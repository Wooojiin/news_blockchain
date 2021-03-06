package com.block.chain.news.web.dto.posts;

import com.block.chain.news.domain.post.Post;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PostEveryResponseDto {
    private Long postId;
    private String title;
    private String author;
    private String banner;
    private String state;

    @Builder
    public PostEveryResponseDto(Post entity){
        this.postId = entity.getPostId();
        this.title = entity.getTitle();
        this.author= entity.getAuthor();
        this.banner = entity.getBanner();
        this.state = entity.getState();
    }
}
