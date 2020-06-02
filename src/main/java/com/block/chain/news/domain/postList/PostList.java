package com.block.chain.news.domain.postList;

import com.block.chain.news.domain.user.User;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class PostList  {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long PostListId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonManagedReference
    private User user;

    @Column(nullable=false)
    private Long postId;

    @Builder
    public PostList(User user, Long postId){
        this.user = user;
        this.postId = postId;
    }


}
