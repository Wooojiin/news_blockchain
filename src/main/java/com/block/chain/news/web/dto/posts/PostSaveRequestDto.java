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
    private String banner;

    @Builder
    public PostSaveRequestDto(String title,String content, String author,String banner){
        this.title = title;
        this.content = content;
        this.author = author;
        this.banner = banner;
    }

    public Post toEntity(String words){
        return Post.builder()
                .title(this.title)
                .content(this.content)
                .author(this.author)
                .topics(words)
                .kinds(-1)
                .banner(this.banner)
                .state("SAVE")
                .build();
    }
}
