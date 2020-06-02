package com.block.chain.news.web.dto.posts;

import com.block.chain.news.domain.post.Post;
import lombok.Getter;

@Getter
public class PostListResponseDto {
    private Long id;
    private String title;
    private String author;
//    private String content;
    private String state;

    public PostListResponseDto(Post entity){
        this.id = entity.getPostId();
        this.title=entity.getTitle();
        this.author = entity.getAuthor();
//        this.content = entity.getContent();
        this.state = entity.getState();
    }
}
