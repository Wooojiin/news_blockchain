package com.block.chain.news.web.dto.user;

import com.block.chain.news.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class UserSaveRequestDto {
    private Long id;
    private String name;
    private String email;
    private String picture;

    @Builder
    public UserSaveRequestDto(Long id, String name, String email, String picture){
        this.id = id;
        this.name = name;
        this.email = email;
        this.picture = picture;
    }

    public User toEntity(){
        return User.builder()
                .name(name)
                .email(email)
                .picture(picture)
                .build();
    }
}
