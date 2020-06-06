package com.block.chain.news.web.dto.posts;

import com.block.chain.news.domain.post.Post;
import com.block.chain.news.domain.subject.Subject;
import com.block.chain.news.web.dto.subject.SubjectItem;
import lombok.Getter;

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
//    private List<SubjectItem> recommendations;
    private Subject subject;
    private int kinds;
    private String banner;
    private LocalDateTime created;
    private List<Post> simmilar;

    public PostResponseDto (Post entity){
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.author = entity.getAuthor();
        this.state = entity.getState();
        if(! (entity.getSelects() == null)){
            this.selected = entity.getSelects().split(",");
        }
        this.topic=  entity.getTopics().split(",");
        this.subject = entity.getSubject();
        this.kinds = entity.getKinds();
        this.banner = entity.getBanner();
//        this.recommendations = recommendations;
        this.created = entity.getCreateDate();
        if(entity.getSubject() != null){
            this.simmilar = entity.getSubject().getPosts();
        }
    }
}
