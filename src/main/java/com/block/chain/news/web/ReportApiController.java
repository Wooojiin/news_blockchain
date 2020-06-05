package com.block.chain.news.web;

import com.block.chain.news.service.ReportService;
import com.block.chain.news.web.dto.posts.PostListResponseDto;
import com.block.chain.news.web.dto.report.ReportAgreeRequestDto;
import com.block.chain.news.web.dto.report.ReportListResponseDto;
import com.block.chain.news.web.dto.report.ReportRequestDto;
import com.block.chain.news.web.dto.report.ReportResponseDto;
import com.block.chain.news.web.dto.user.UserSaveRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ReportApiController {
    private final ReportService reportService;

    @GetMapping("/api/v1/report/")
    public ResponseEntity<List<ReportListResponseDto>> findAll(){
        return new ResponseEntity<List<ReportListResponseDto>>(reportService.getAll(),HttpStatus.OK);
    }

    @GetMapping("/api/v1/report/{reportId}")
    public ResponseEntity<ReportResponseDto> findOne(@PathVariable Long reportId){
        return new ResponseEntity<ReportResponseDto>(reportService.getOne(reportId), HttpStatus.OK);
    }

    @PostMapping("/api/v1/report/{postId}/")
    public ResponseEntity<Long> report(@PathVariable Long postId,
                                       @RequestBody ReportRequestDto reportRequestDto){
        return new ResponseEntity<Long>(reportService.report(postId, reportRequestDto.getUserEmail(), reportRequestDto.getContents()), HttpStatus.OK);
    }

    @PutMapping("/api/v1/report/{reportId}/")
    public ResponseEntity<Long> agree(@PathVariable Long reportId,
                                      @RequestBody ReportAgreeRequestDto reportAgreeRequestDto){
        return new ResponseEntity<Long>(reportService.agree(reportId, reportAgreeRequestDto.getUserEmail(), reportAgreeRequestDto.getAgree()), HttpStatus.OK);
    }

}

