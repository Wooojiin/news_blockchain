package com.block.chain.news.domain.post;

import com.block.chain.news.domain.BaseTimeEntity;
import com.block.chain.news.domain.subject.Subject;
import com.block.chain.news.domain.tags.Tags;
import com.block.chain.news.domain.tags.TagsRepository;
import com.block.chain.news.domain.topic.Topic;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
//    @Column(nullable=false, length=255)
//    private String content;

    @Column(length=100, nullable=false)
    private String state;

//    @OneToMany(mappedBy = "post")
//    @JsonBackReference
//    private List<Topic> topic = new ArrayList<>();

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonManagedReference
    private Subject subject;

    @Column(nullable=false)
    private String topics;

//    @OneToMany(mappedBy="post")
//    @JsonBackReference
//    private List<Topic> selected = new ArrayList<>();

    @Column(nullable=true)
    private String selects;

    @Column(nullable=false)
    private String select;

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


