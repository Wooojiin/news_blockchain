package com.block.chain.news.web.dto.posts;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PostDeployRequestDto {
    private int kinds;
    private Long subjectId;
    private String banner;
    @Builder
    public PostDeployRequestDto(int kinds, Long subjectId,String banner){
        this.kinds = kinds;
        this.subjectId = subjectId;
        this.banner = banner;
    }
}
