package com.block.chain.news.service;

import com.block.chain.news.domain.follow.Follow;
import com.block.chain.news.domain.follow.FollowRepository;
import com.block.chain.news.domain.user.User;
import com.block.chain.news.domain.user.UserRepository;
import com.block.chain.news.web.dto.follow.FollowRequestDto;
import com.block.chain.news.web.dto.follow.FollowResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class FollowService {
    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    @Transactional
    public String follow(FollowRequestDto requestDto){
//        User fromUser = userRepository.findByEmail(requestDto.getFromUserEmail())
//                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 없습니다. email =" + requestDto.getFromUserEmail()));
//
//        User toUser = userRepository.findByEmail(requestDto.getToUserEmail())
//                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 없습니다. email =" + requestDto.getToUserEmail()));

//        List<String> following = followRepository.findAllBy

//        List<Follow> following = followRepository.findAllByFromUser(requestDto.getFromUserEmail());
//
//        if(!following.contains(requestDto.getToUserEmail())){
//            followRepository.save(Follow.builder()
//                    .fromUser(requestDto.getFromUserEmail())
//                    .toUser(requestDto.getToUserEmail())
//                    .build());
//        }
        followRepository.save(Follow.builder()
                .fromUser(requestDto.getFromUserEmail())
                .toUser(requestDto.getToUserEmail())
                .build());

        return requestDto.getToUserEmail();
    }

    @Transactional
    public String unFollow(String fromUserEmail, String toUserEmail){
//        User fromUser = userRepository.findByEmail(fromUserEmail)
//                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 없습니다. email =" + fromUserEmail));
//
//        User toUser = userRepository.findByEmail(toUserEmail)
//                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 없습니다. email =" + toUserEmail));
//
//        List<String> following = new FollowResponseDto(fromUser).getFollowing();

//        if(following.contains(toUserEmail)){
//            Follow follow = followRepository.findByFromUserAndToUser(fromUser, toUser)
//                    .orElseThrow(() -> new IllegalArgumentException("해당 팔로우 정보가 없습니다."));
//            System.out.println("follow id : " + follow.getFollowId());
//
//            followRepository.deleteFollow(follow.getFollowId());
//        }
        Follow follow = followRepository.findByFromUserAndToUser(fromUserEmail, toUserEmail)
                    .orElseThrow(() -> new IllegalArgumentException("해당 팔로우 정보가 없습니다."));
        followRepository.deleteFollow(follow.getFollowId());

        return toUserEmail;
    }

    @Transactional
    public FollowResponseDto GetFollow(String fromemail) {
//        User user = userRepository.findByEmail(email)
//                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 없습니다. email =" + email));

        List<Follow> followings = followRepository.findAllByFromUser(fromemail);
        List<Follow> followers = followRepository.findAllByToUser(fromemail);

        return new FollowResponseDto(followers, followings);
    }
}
