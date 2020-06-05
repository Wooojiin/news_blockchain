package com.block.chain.news.web.dto.report;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ReportRequestDto {
    private String userEmail;
    private String contents;

    @Builder
    public ReportRequestDto(String userEmail, String contents){
        this.userEmail = userEmail;
        this.contents = contents;
    }
}
