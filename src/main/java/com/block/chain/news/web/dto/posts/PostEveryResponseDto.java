package com.block.chain.news.web.dto.posts;

import com.block.chain.news.domain.post.Post;

public class PostEveryResponseDto {
    private Long id;
    private String title;
    private String author;
    private String banner;
    private String state;

    public PostEveryResponseDto(Post entity){
        this.id = entity.getPostId();
        this.title = entity.getTitle();
        this.author= entity.getAuthor();
        this.banner = entity.getBanner();
        this.state = entity.getState();
    }
}
