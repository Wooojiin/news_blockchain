package com.block.chain.news.domain.user;

import com.block.chain.news.domain.BaseTimeEntity;
import com.block.chain.news.domain.advertisement.Advertisement;
import com.block.chain.news.domain.follow.Follow;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class User extends BaseTimeEntity {
    @Id
    private Long userId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    private String picture;

    @Column(nullable = false)
    private String role;

    @OneToMany(mappedBy = "fromUser")
    @JsonBackReference
    private List<Follow> follower;

    @OneToMany(mappedBy = "toUser")
    @JsonBackReference
    private List<Follow> following;

    @OneToMany(mappedBy = "user")
    @JsonBackReference
    private List<Advertisement> advertisements;

    @Builder
    public User(Long userId, String name, String email, String picture, String role){
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.role = role;
    }
}
