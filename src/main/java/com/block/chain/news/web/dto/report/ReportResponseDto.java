package com.block.chain.news.web.dto.report;

import com.block.chain.news.domain.post.Post;
import com.block.chain.news.domain.report.Report;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class ReportResponseDto {
    private Post post;
    private String reporter;
    private String contents;
    private int agree;
    private int disagree;
    private LocalDate endDate;


    @Builder
    public ReportResponseDto(Report entity){
        this.post = entity.getPost();
        this.reporter = entity.getReporter();
        this.contents = entity.getContents();
        this.agree = entity.getAgree();
        this.disagree = entity.getDisagree();
        this.endDate = entity.getEndDate();
    }
}
