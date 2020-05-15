package com.block.chain.news.domain.user;

import com.block.chain.news.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class User extends BaseTimeEntity {
    @Id
    private Long userId;

    private String name;

    @Column(nullable = false)
    private String email;

    private String picture;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Builder
    public User(Long userId, String name, String email, String picture){
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.picture = picture;
    }
}
