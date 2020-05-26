package com.block.chain.news.domain.user;

import com.block.chain.news.domain.BaseTimeEntity;
import com.block.chain.news.domain.follow.Follow;
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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @OneToMany(mappedBy = "fromUser")
    private List<Follow> follower;

    @OneToMany(mappedBy = "toUser")
    private List<Follow> following;

    @Builder
    public User(String name, String email, String picture){
        this.name = name;
        this.email = email;
        this.picture = picture;
    }
}
