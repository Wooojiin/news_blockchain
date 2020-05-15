package com.block.chain.news.web;

import com.block.chain.news.service.UserService;
import com.block.chain.news.web.dto.UserSaveRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserService userService;

    @PostMapping("/api/v1/user")
    public ResponseEntity<String> save(@RequestBody UserSaveRequestDto requestDto){
        log.info("User save email : {}", requestDto.getEmail());

        return new ResponseEntity<String>(userService.save(requestDto), HttpStatus.OK);
    }
}
