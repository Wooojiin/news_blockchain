package com.block.chain.news.web;

import com.block.chain.news.service.FollowService;
import com.block.chain.news.service.UserService;
import com.block.chain.news.web.dto.follow.FollowRequestDto;
import com.block.chain.news.web.dto.follow.FollowResponseDto;
import com.block.chain.news.web.dto.user.UserResponseDto;
import com.block.chain.news.web.dto.user.UserSaveRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
public class UserApiController {
    private final UserService userService;
    private final FollowService followService;

    @PostMapping("/api/v1/user")
    public ResponseEntity<String> save(@RequestBody UserSaveRequestDto requestDto){
        log.info("User save email : {}", requestDto.getEmail());

        return new ResponseEntity<String>(userService.save(requestDto), HttpStatus.OK);
    }

    @GetMapping("/api/v1/user/{id}")
    public ResponseEntity<UserResponseDto> findById(@PathVariable Long id){
        log.info("User get info");

        return new ResponseEntity<UserResponseDto>(userService.findById(id), HttpStatus.OK);
    }

    @PutMapping("/api/v1/user/follow")
    public ResponseEntity<String> follow(@RequestBody FollowRequestDto requestDto){
        log.info("Follow");

        return new ResponseEntity<String>(followService.follow(requestDto), HttpStatus.OK);
    }

    @GetMapping("/api/v1/user/follow/{email}")
    public ResponseEntity<FollowResponseDto> getFollow(@PathVariable String email){
        log.info("Get Follow User : {}", email);

        return new ResponseEntity<FollowResponseDto>(followService.GetFollow(email), HttpStatus.OK);
    }

}
