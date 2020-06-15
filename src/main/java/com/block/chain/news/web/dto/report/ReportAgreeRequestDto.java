package com.block.chain.news.web.dto.report;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ReportAgreeRequestDto {
    private int agree;
    private String userEmail;

    @Builder
    public ReportAgreeRequestDto(int agree, String userEmail){
        this.agree = agree;
        this.userEmail = userEmail;
    }
}
