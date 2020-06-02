package com.block.chain.news.web.dto.posts;

import com.block.chain.news.domain.post.Post;
import com.block.chain.news.domain.subject.Subject;
import lombok.Getter;

import java.util.List;

@Getter
public class PostResponseDto {
    private String title;
    private String content;
    private String author;
    private String state;
    private String [] topic;
    private List<Subject> recommendations;
    private Subject subject;

    public PostResponseDto (Post entity, List<Subject> recommendations){
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.author = entity.getAuthor();
        this.state = entity.getState();
        this.topic=  entity.getTopics().split(",");
        this.subject = entity.getSubject();
        this.recommendations = recommendations;
    }
}
