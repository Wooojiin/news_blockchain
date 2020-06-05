package com.block.chain.news.web.dto.posts;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PostDeployRequestDto {
    private String [] selected;
    private Long subjectId;

    @Builder
    public PostDeployRequestDto(String[] selected, Long subjectId){
        this.selected = selected;
        this.subjectId = subjectId;
    }
}
