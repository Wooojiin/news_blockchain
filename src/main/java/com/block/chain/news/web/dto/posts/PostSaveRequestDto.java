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

    @Builder
    public PostSaveRequestDto(String title,String content, String author, int kinds){
        this.title = title;
        this.content = content;
        this.author = author;
        this.kinds = kinds;
    }

    public Post toEntity(String words){
        return Post.builder()
                .title(this.title)
                .content(this.content)
                .author(this.author)
                .topics(words)
                .kinds(this.kinds)
                .state("SAVE")
                .build();
    }
}
