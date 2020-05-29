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
    private String role;

    @Builder
    public UserSaveRequestDto(Long id, String name, String email, String picture, String role){
        this.id = id;
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.role = role;
    }

    public User toEntity(){
        return User.builder()
                .userId(id)
                .name(name)
                .email(email)
                .picture(picture)
                .role(role)
                .build();
    }
}
