package com.block.chain.news.web.dto.user;

import com.block.chain.news.domain.user.User;
import lombok.Getter;

@Getter
public class UserResponseDto {
    private Long id;
    private String name;
    private String email;
    private String picture;

    public UserResponseDto(User entity){
        this.id = entity.getUserId();
        this.name = entity.getName();
        this.email = entity.getEmail();
        this.picture = entity.getPicture();
    }
}
