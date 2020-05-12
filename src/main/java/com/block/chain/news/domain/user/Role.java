package com.block.chain.news.domain.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {

    JOURNALIST("ROLE_JOURNALIST", "기자"),
    USER("ROLE_USER", "일반 사용자");

    private  final String key;
    private  final String title;
}
