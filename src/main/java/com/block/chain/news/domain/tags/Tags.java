package com.block.chain.news.domain.tags;

import com.block.chain.news.domain.post.Post;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Tags {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long SelectedId;

    @Column(columnDefinition="TEXT", nullable=false)
    private String content;
//    @Column(length=300, nullable=false)
//    private String content;

    @Column(columnDefinition="DOUBLE", nullable=false)
    private Double similarity;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonManagedReference
    private Post post;

    @Builder
    public Tags(String content,Post post){
        this.content = content;
        this.post= post;
    }
}
