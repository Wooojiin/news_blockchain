package com.block.chain.news.web.dto.posts;

import com.block.chain.news.domain.post.Post;
import com.block.chain.news.domain.subject.Subject;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
public class PostResponseDto {
    private String title;
    private String content;
    private String author;
    private String state;
    private String [] topic;
    private String [] selected;
    private List<Subject> recommendations;
    private Subject subject;
    private int kinds;
    private String banner;
    private LocalDateTime created;
    private List<Post> simmilar;

    public PostResponseDto (Post entity, List<Subject> recommendations){
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.author = entity.getAuthor();
        this.selected = entity.getSelects().split(",");
        this.state = entity.getState();
        this.topic=  entity.getTopics().split(",");
        this.subject = entity.getSubject();
        this.kinds = entity.getKinds();
        this.banner = entity.getBanner();
        this.recommendations = recommendations;
        this.created = entity.getCreateDate();
        this.simmilar = entity.getSubject().getPosts();
    }
}
