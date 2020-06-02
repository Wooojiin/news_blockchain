package com.block.chain.news.domain.subject;

import com.block.chain.news.domain.BaseTimeEntity;
import com.block.chain.news.domain.post.Post;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Entity
public class Subject extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long SubjectId;

    @Column(nullable=false)
    private String title;


    @OneToMany(mappedBy = "subject")
    @JsonBackReference
    private List<Post> posts = new ArrayList<>();

    @Builder
    public Subject(String title, List<Post> posts){
        this.title = title;
        this.posts = posts;
    }

    public void addPost(Post post){
        List<Post> newPosts = this.posts;
        newPosts.add(post);
        this.posts = newPosts;
    }

}
