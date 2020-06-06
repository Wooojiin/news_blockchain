package com.block.chain.news.web.dto.posts;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PostUpdateDto {
    private String title;
    private String author;
    private String content;
    private int kinds;
    private String banner;

    @Builder
    public PostUpdateDto(String title, String author, String content, int kinds, String banner){
        this.title = title;
        this.author = author;
        this.content=  content;
        this.kinds = kinds;
        this.banner = banner;
    }
}
