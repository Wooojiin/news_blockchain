package com.block.chain.news.domain.post;

import com.block.chain.news.domain.BaseTimeEntity;
import com.block.chain.news.domain.subject.Subject;
import com.block.chain.news.web.dto.posts.PostUpdateDto;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @Column(nullable=false)
    private String title;

    @Column(nullable=false)
    private String author;

    @Column(nullable=false, columnDefinition = "TEXT")
    private String content;

    @Column(length=100, nullable=false)
    private String state;

    @Column(nullable=false)
    private int kinds;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonBackReference
    private Subject subject;

    @Column(nullable=false)
    private String topics;

    @Column(nullable=false)
    private String banner;

    @Column(nullable=true)
    private String selects;

    @Builder
    public Post(String title, String author, String content, String state, String topics, int kinds, String banner){
        this.title = title;
        this.author = author;
        this.content=content;
        this.state=  state;
        this.topics = topics;
        this.kinds = kinds;
        this.banner = banner;
    }

    public void updateState(String state) {
        this.state = state;
    }
    public void updateSelect(String select){
        this.selects = select;
    }
    public void updateSubject(Subject subject){
        this.subject = subject;
    }
    public void updatePost(PostUpdateDto entity){
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.kinds = entity.getKinds();
        this.banner = entity.getBanner();
    }
}


