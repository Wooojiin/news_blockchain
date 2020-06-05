package com.block.chain.news.web.dto.posts;

import com.block.chain.news.domain.post.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostSaveRequestDto {
    private String title;
    private String content;
    private String author;
    private int kinds;
    private String banner;

    @Builder
    public PostSaveRequestDto(String title,String content, String author, int kinds, String banner){
        this.title = title;
        this.content = content;
        this.author = author;
        this.kinds = kinds;
        this.banner = banner;
    }

    public Post toEntity(String words){
        return Post.builder()
                .title(this.title)
                .content(this.content)
                .author(this.author)
                .topics(words)
                .banner(this.banner)
                .kinds(this.kinds)
                .state("SAVE")
                .build();
    }
}
