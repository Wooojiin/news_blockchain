package com.block.chain.news.web.dto.follow;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FollowRequestDto {

    private String fromUserEmail;  // 나의 아이디
    private String toUserEmail;  // 팔로우 대상의 아이디

}
