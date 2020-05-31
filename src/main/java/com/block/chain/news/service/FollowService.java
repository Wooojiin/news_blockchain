package com.block.chain.news.service;

import com.block.chain.news.domain.follow.Follow;
import com.block.chain.news.domain.follow.FollowRepository;
import com.block.chain.news.domain.user.User;
import com.block.chain.news.domain.user.UserRepository;
import com.block.chain.news.web.dto.user.FollowRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class FollowService {
    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    @Transactional
    public String follow(FollowRequestDto requestDto){
        User fromUser = userRepository.findByEmail(requestDto.getFromUserEmail())
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 없습니다. email =" + requestDto.getFromUserEmail()));

        User toUser = userRepository.findByEmail(requestDto.getToUserEmail())
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 없습니다. email =" + requestDto.getFromUserEmail()));

        followRepository.save(Follow.builder()
            .fromUser(fromUser)
            .toUser(toUser)
            .build());

        return toUser.getEmail();
    }

}
