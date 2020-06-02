package com.block.chain.news.domain.post;

import com.block.chain.news.domain.BaseTimeEntity;
import com.block.chain.news.domain.subject.Subject;
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

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonManagedReference
    private Subject subject;

    @Column(nullable=false)
    private String topics;

    @Column(nullable=true)
    private String selects;

    @Builder
    public Post(String title, String author, String content, String state, String topics){
        this.title = title;
        this.author = author;
        this.content=content;
        this.state=  state;
        this.topics = topics;
    }

    public void updateState(String state) {
        this.state = state;
    }
    public void updateSelect(String select){
        this.selects = select;
    }

}


