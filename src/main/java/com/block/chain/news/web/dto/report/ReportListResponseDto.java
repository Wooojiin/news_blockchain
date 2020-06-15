package com.block.chain.news.web.dto.report;

import com.block.chain.news.domain.post.Post;
import com.block.chain.news.domain.report.Report;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ReportListResponseDto {
    private Post post;
    private int agree;
    private int disagree;

    @Builder
    public ReportListResponseDto(Report entity){
        this.post = entity.getPost();
        this.agree = entity.getAgree();
        this.disagree = entity.getDisagree();
    }
}
