package com.block.chain.news.web.dto.posts;

import com.block.chain.news.domain.post.Post;
import lombok.Getter;

@Getter
public class PostFollowerCheckDto {
    private Long postId;
    private String title;
    private String author;
    private String banner;
    private String state;
    private Boolean followChk;

    public PostFollowerCheckDto(Post entity, Boolean isFollow){
        this.postId = entity.getPostId();
        this.title = entity.getTitle();
        this.author= entity.getAuthor();
        this.banner = entity.getBanner();
        this.state = entity.getState();
        this.followChk = isFollow;
    }
}
