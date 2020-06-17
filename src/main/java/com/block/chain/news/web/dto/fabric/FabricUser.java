package com.block.chain.news.web.dto.fabric;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FabricUser {
    String userId; //  회원 email
    String role;

    public FabricUser(String userId, String role){
        this.userId = userId;
        this.role = role;
    }
}
