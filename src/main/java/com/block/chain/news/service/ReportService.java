package com.block.chain.news.service;

import com.block.chain.news.domain.post.Post;
import com.block.chain.news.domain.post.PostRepository;
import com.block.chain.news.domain.report.Report;
import com.block.chain.news.domain.report.ReportRepository;
import com.block.chain.news.domain.reportList.ReportList;
import com.block.chain.news.domain.reportList.ReportListRepository;
import com.block.chain.news.domain.user.User;
import com.block.chain.news.domain.user.UserRepository;
import com.block.chain.news.web.dto.report.ReportListResponseDto;
import com.block.chain.news.web.dto.report.ReportResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ReportService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final ReportRepository reportRepository;
    private final ReportListRepository reportListRepository;

    public List<ReportListResponseDto> getAll(){
        List<ReportListResponseDto> resultSet = new LinkedList<>();
        List<Report> reports = reportRepository.findAll();
        for(Report report : reports){
            ReportListResponseDto reportListResponseDto = new ReportListResponseDto(report);
            resultSet.add(reportListResponseDto);
        }
        return resultSet;
    }

    public ReportResponseDto getOne(Long reportId){
        Report report = reportRepository.findById(reportId).orElseThrow(() -> new IllegalArgumentException("잘못된 Report!"));
        return new ReportResponseDto(report);
    }
    public Long report(Long postId, String userEmail, String contents){
        Post post = postRepository.findById(postId)
                .orElseThrow( () -> new IllegalArgumentException("잘못된 기사를 선택하셨습니다"));
        post.updateState("REPORTED");
        Report report = Report.builder()
                .post(post)
                .reporter(userEmail)
                .contents(contents)
                .build();
        reportRepository.save(report);
        return report.getReportId();
    }

    @Transactional
    public Long agree(Long reportId, String userEmail, int agree){
        Report report = reportRepository.findById(reportId).orElseThrow(() -> new IllegalArgumentException("잘못된 요청을 보내셨습니다"));
        if (agree == 1){
            report.agree();
        }else{
            report.disagree();
        }
        User user = userRepository.findByEmail(userEmail).orElseThrow(() -> new IllegalArgumentException("뭔가 잘 못 되었대여"));
        ReportList reportList = ReportList.builder()
                .user(user)
                .reportId(reportId)
                .decision(agree)
                .build();
        reportListRepository.save(reportList);
        return reportId;
    }
}
