package com.block.chain.news.domain.follow;

import com.block.chain.news.domain.BaseTimeEntity;
import com.block.chain.news.domain.user.User;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Follow extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long followId;

    // from -> to   following
    // to -> from   follower

    @Column(nullable = false)
    private String fromUser;

    @Column(nullable = false)
    private String toUser;

    @Builder
    public Follow(String fromUser, String toUser){
        this.fromUser = fromUser;
        this.toUser = toUser;
    }

//    @ManyToOne(cascade = CascadeType.ALL)
//    @JsonManagedReference
//    private User fromUser;
//
//    @ManyToOne(cascade = CascadeType.ALL)
//    @JsonManagedReference
//    private User toUser;
//
//    @Builder
//    public Follow(User fromUser, User toUser){
//        this.fromUser = fromUser;
//        this.toUser = toUser;
//    }
}
