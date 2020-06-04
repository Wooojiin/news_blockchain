package com.block.chain.news.web.dto.follow;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FollowRequestDto {
    // 나의 이이디
    // 팔로우 대상의 아이디

    private String fromUserEmail;
    private String toUserEmail;

}
