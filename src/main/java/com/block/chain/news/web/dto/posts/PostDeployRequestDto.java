package com.block.chain.news.web.dto.posts;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PostDeployRequestDto {
    private int kinds;
    private Long subjectId;

    @Builder
    public PostDeployRequestDto(int kinds, Long subjectId){
        this.kinds = kinds;
        this.subjectId = subjectId;
    }
}
