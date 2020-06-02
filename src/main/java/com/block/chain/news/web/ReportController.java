package com.block.chain.news.web;

import com.block.chain.news.service.ReportService;
import com.block.chain.news.web.dto.posts.PostListResponseDto;
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
public class ReportController {
    private final ReportService reportService;

    @GetMapping("/api/v1/report/{postId}/{userId}")
    public ResponseEntity<Long> report(@PathVariable Long postId,
                       @PathVariable Long userId){
        return new ResponseEntity<Long>(reportService.report(postId, userId), HttpStatus.OK);
    }

    @PutMapping("/api/v1/report/{reportId}/{agree}")
    public ResponseEntity<Long> agree(@PathVariable Long reportId,
                                      @PathVariable int agree,
                                      @RequestParam(value="userId") Long userId){
        return new ResponseEntity<Long>(reportService.agree(reportId, userId, agree), HttpStatus.OK);
    }

}

